package com.vn.topcv.service.impl;

import com.vn.topcv.dto.reponse.UserLoginResponse;
import com.vn.topcv.dto.reponse.UserRegisterResponse;
import com.vn.topcv.dto.request.UserLogoutRequest;
import com.vn.topcv.dto.request.UserRegisterRequest;
import com.vn.topcv.dto.request.UserLoginRequest;
import com.vn.topcv.entity.Company;
import com.vn.topcv.entity.Personal;
import com.vn.topcv.entity.Role;
import com.vn.topcv.entity.Token;
import com.vn.topcv.entity.User;
import com.vn.topcv.entity.enums.ERole;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.jwt.JwtService;
import com.vn.topcv.repository.ICompanyRepository;
import com.vn.topcv.repository.IPersonalRepository;
import com.vn.topcv.repository.IRoleRepository;
import com.vn.topcv.repository.ITokenRepository;
import com.vn.topcv.repository.IUserRepository;
import com.vn.topcv.service.IAuthService;
import com.vn.topcv.util.ResponseObject;
import com.vn.topcv.validation.RequestValidation;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IRoleRepository roleRepository;

  @Autowired
  private IPersonalRepository personalRepository;

  @Autowired
  private ICompanyRepository companyRepository;

  @Autowired
  private ITokenRepository tokenRepository;;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  @Override
  public ResponseEntity<ResponseObject> register(UserRegisterRequest request) {
	ResponseObject responseObject;
	UserRegisterResponse responseData;
	HttpStatus status;

	boolean isValid = true;
	String errorMessage = "";

	try {
	  if (userRepository.findByEmail(request.getEmail()).isPresent()) {
		throw new CustomException("This email has been registered!");
	  }

	  if (!RequestValidation.isValidEmail(request.getEmail())) {
		errorMessage += "Email cannot be empty, and must be in the correct format.";
		isValid = false;
	  }

	  if (!RequestValidation.isValidPassword(request.getPassword())) {
		errorMessage += "Password cannot be empty, and must be between 8 and 30 characters.";
		isValid = false;
	  }

	  if (isValid) {
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setCreateDate(new Timestamp(System.currentTimeMillis()));

		ERole roleName;

		if (request.getRoleName().equals("company")) {
		  roleName = ERole.ROLE_COMPANY;
		} else if (request.getRoleName().equals("personal")) {
		  roleName = ERole.ROLE_PERSONAL;
		} else {
		  throw new CustomException("Invalid role!");
		}

		Role role = roleRepository.findByRoleName(roleName)
			.orElseGet(() -> {
			  Role newRole = new Role();
			  newRole.setRoleName(roleName);
			  return roleRepository.save(newRole);
			});

		user.setRoles(Set.of(role));
		userRepository.save(user);

		if (roleName == ERole.ROLE_COMPANY) {
		  Company company = new Company();
		  company.setUser(user);
		  company.setCreateDate(new Timestamp(System.currentTimeMillis()));
		  companyRepository.save(company);
		} else {
		  Personal personal = new Personal();
		  personal.setUser(user);
		  personal.setCreateDate(new Timestamp(System.currentTimeMillis()));
		  personalRepository.save(personal);
		}

		responseData = UserRegisterResponse.builder()
			.email(user.getEmail())
			.password(user.getPassword())
			.roles(user.getRoles())
			.build();

		responseObject = ResponseObject.builder()
			.status(HttpStatus.OK.name())
			.message("Create user successfully")
			.data(responseData)
			.build();

		status = HttpStatus.CREATED;
	  } else {
		responseObject = ResponseObject.builder()
			.status(HttpStatus.BAD_REQUEST.name())
			.message(errorMessage)
			.build();

		status = HttpStatus.BAD_REQUEST;
	  }

	} catch (CustomException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.CONFLICT.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.CONFLICT;
	} catch (Exception e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();
	  status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<>(responseObject, status);
  }

  @Override
  public ResponseEntity<ResponseObject> login(UserLoginRequest request) {
	ResponseObject responseObject;
	UserLoginResponse responseData;
	HttpStatus status;

	boolean isValid = true;
	String errorMessage = "";

	try {

	  if (!RequestValidation.isValidEmail(request.getEmail())) {
		errorMessage += "Email cannot be empty, and must be in the correct format.";
		isValid = false;
	  }

	  if (!RequestValidation.isValidPassword(request.getPassword())) {
		errorMessage += "Password cannot be empty, and must be between 8 and 30 characters.";
		isValid = false;
	  }

	  if (isValid) {
		User user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new CustomException("This email is not registered!"));

		if(tokenRepository.findByUserId(user.getUserId()).isPresent()) {
		  throw new CustomException("This user is already login!");
		}

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
		  throw new CustomException("Wrong password!");
		}

		String jwtToken = jwtService.generateToken(user);
		String jwtRefreshToken = jwtService.generateRefreshToken(user);
		Date expiryDate = jwtService.getExpirationTimeFromToken(jwtToken);
		Token token = new Token();

		token.setTokenString(jwtToken);
		token.setRefreshToken(jwtRefreshToken);
		token.setExpire(expiryDate);
		token.setUser(user);
		tokenRepository.save(token);

		responseData = UserLoginResponse.builder()
			.user(user)
			.token(jwtToken)
			.refreshToken(jwtRefreshToken)
			.expires(expiryDate)
			.build();

		responseObject = ResponseObject.builder()
			.status(HttpStatus.OK.name())
			.message("Login successfully!")
			.data(responseData)
			.build();

		status = HttpStatus.OK;
	  } else {
		responseObject = ResponseObject.builder()
			.status(HttpStatus.BAD_REQUEST.name())
			.message(errorMessage)
			.build();

		status = HttpStatus.BAD_REQUEST;
	  }
	} catch (CustomException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.UNAUTHORIZED.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.UNAUTHORIZED;
	} catch (Exception e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<>(responseObject, status);
  }

  @Override
  public ResponseEntity<ResponseObject> logout(UserLogoutRequest request) {
	ResponseObject responseObject;
	HttpStatus status;

	try{
	  if(request.getToken().isEmpty()){
		throw new CustomException("Token is empty!");
	  }

	  Token token = tokenRepository.findTokenByTokenString(request.getToken()).orElseThrow(() -> new CustomException("Token not found!"));

	  tokenRepository.delete(token);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Logout successfully!")
		  .data(token)
		  .build();

	  status = HttpStatus.OK;

	} catch (CustomException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.UNAUTHORIZED.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.UNAUTHORIZED;
	} catch (Exception e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<>(responseObject, status);
  }
}
