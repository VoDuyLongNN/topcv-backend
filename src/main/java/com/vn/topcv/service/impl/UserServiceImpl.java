package com.vn.topcv.service.impl;

import com.vn.topcv.dto.request.UserChangePasswordRequest;
import com.vn.topcv.entity.Company;
import com.vn.topcv.entity.Personal;
import com.vn.topcv.entity.User;
import com.vn.topcv.entity.enums.ERole;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.ICompanyRepository;
import com.vn.topcv.repository.IPersonalRepository;
import com.vn.topcv.repository.ITokenRepository;
import com.vn.topcv.repository.IUserRepository;
import com.vn.topcv.service.IUserService;
import com.vn.topcv.util.ResponseObject;
import com.vn.topcv.validation.RequestValidation;
import io.jsonwebtoken.JwtException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private IUserRepository userRepository;

  @Autowired
  private IPersonalRepository personalRepository;

  @Autowired
  private ICompanyRepository companyRepository;

  @Autowired
  private ITokenRepository tokenRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public ResponseEntity<ResponseObject> changePassword(UserChangePasswordRequest request) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try{
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
		throw new CustomException("Old password does not match!");
	  }

	  if(request.getNewPassword().isEmpty()){
		throw new CustomException("Password is empty!");
	  }

	  if(request.getOldPassword().equals(request.getNewPassword())){
		throw new CustomException("The new password matches the old password!");
	  }

	  if(!request.getNewPassword().equals(request.getReNewPassword())){
		throw new CustomException("The new password and the re-entered password do not match!");
	  }

	  if(!RequestValidation.isValidPassword(request.getNewPassword())){
		throw new CustomException("The new password is invalid!");
	  }

	  user.setPassword(passwordEncoder.encode(request.getNewPassword()));
	  userRepository.save(user);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Update password successfully!")
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (CustomException e){
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.BAD_REQUEST;
	} catch (Exception e){
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<>(responseObject, httpStatus);
  }

  @Override
  public ResponseEntity<ResponseObject> uploadAvt(MultipartFile file) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  if(file.isEmpty()){
		throw new CustomException("File name is empty!");
	  }

	  String fileName = file.getOriginalFilename();
	  Path path = Paths.get("avatars/" + fileName);
	  Files.write(path, file.getBytes());
	  Map<String, String> response = new HashMap<>();
	  response.put("avtUrl", fileName);

	  user.setAvt(fileName);
	  userRepository.save(user);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Upload file successfully")
		  .data(response)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (IOException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.BAD_REQUEST;
	} catch (Exception e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<>(responseObject, httpStatus);
  }

  @Override
  public ResponseEntity<ResponseObject> getAvt() {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try{
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  String avtUrl = user.getAvt();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get avt successfully!")
		  .data(avtUrl)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (CustomException e){
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.NOT_FOUND.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.NOT_FOUND;
	} catch (Exception e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<>(responseObject, httpStatus);
  }
}
