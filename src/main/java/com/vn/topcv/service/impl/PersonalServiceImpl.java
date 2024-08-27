package com.vn.topcv.service.impl;

import com.vn.topcv.dto.reponse.PersonalGetByPostIdResponse;
import com.vn.topcv.dto.request.PersonalUpdateRequest;
import com.vn.topcv.entity.Personal;
import com.vn.topcv.entity.PostPersonal;
import com.vn.topcv.entity.User;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.IPersonalRepository;
import com.vn.topcv.repository.IPostPersonalRepository;
import com.vn.topcv.service.IPersonalService;
import com.vn.topcv.util.ResponseObject;
import com.vn.topcv.validation.RequestValidation;
import io.jsonwebtoken.JwtException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PersonalServiceImpl implements IPersonalService {

  @Autowired
  IPersonalRepository personalRepository;

  @Autowired
  IPostPersonalRepository postPersonalRepository;

  @Override
  public ResponseEntity<ResponseObject> getCurrentPersonal() {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Personal personal = personalRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get personal user successfully!")
		  .data(personal)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (CustomException | JwtException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.UNAUTHORIZED.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.UNAUTHORIZED;
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
  public ResponseEntity<ResponseObject> updatePersonalUser(PersonalUpdateRequest request) {
	ResponseObject responseObject;
	HttpStatus status;
	String message = "";

	boolean isValid = true;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Personal personal = personalRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  if (!RequestValidation.isValidName(request.getFirstName())) {
		message += "First name is invalid!. ";
		isValid = false;
	  }

	  if (!RequestValidation.isValidName(request.getLastName())) {
		message += "Last name is invalid!. ";
		isValid = false;
	  }

	  if (!RequestValidation.isValidPhoneNumber(request.getPhone())) {
		message += "Phone number is invalid!. ";
		isValid = false;
	  }

	  if (!RequestValidation.isBoolean(request.getGender())) {
		message += "Gender is invalid!. ";
		isValid = false;
	  }

	  if (!RequestValidation.isValidLocalDate(request.getBirthDay())) {
		message += "Birthday is invalid!. ";
		isValid = false;
	  }

	  if (isValid) {
		personal.setFirstName(request.getFirstName());
		personal.setLastName(request.getLastName());
		personal.setPhone(request.getPhone());
		personal.setGender(Boolean.parseBoolean(request.getGender()));

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		personal.setBirthDay(LocalDate.parse(request.getBirthDay(), dateFormatter));

		if (!request.getAddress().isEmpty()) {
		  personal.setAddress(request.getAddress());
		}

		if (!request.getLocation().isEmpty()) {
		  personal.setLocation(request.getLocation());
		}

		if (!request.getEducation().isEmpty()) {
		  personal.setEducation(request.getEducation());
		}

		if (!request.getSkill().isEmpty()) {
		  personal.setSkill(request.getSkill());
		}

		if (!request.getDesc().isEmpty()) {
		  personal.setDescription(request.getDesc());
		}

		personal.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		personalRepository.save(personal);

		responseObject = ResponseObject.builder()
			.status(HttpStatus.OK.name())
			.message("Cập nhập thành công!")
			.data(personal)
			.build();

		status = HttpStatus.OK;
	  } else {
		responseObject = ResponseObject.builder()
			.status(HttpStatus.BAD_REQUEST.name())
			.message(message)
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
  public ResponseEntity<ResponseObject> getPersonalByPostId(String id) {
	ResponseObject responseObject;
	HttpStatus status;

	try {
	  Long postId = Long.parseLong(id);
	  String birth = null;

	  PostPersonal postPersonal = postPersonalRepository.findById(postId)
		  .orElseThrow(() -> new CustomException("Không tìm thấy bài đăng!"));

	  Personal personal = postPersonal.getPersonal();

	  if(personal.getBirthDay() != null){
		birth = personal.getBirthDay().toString();
	  }

	  PersonalGetByPostIdResponse response = PersonalGetByPostIdResponse.builder()
		  .personalId(personal.getId().toString())
		  .email(personal.getUser().getEmail())
		  .avtUrl(personal.getUser().getAvt())
		  .firstName(personal.getFirstName())
		  .lastName(personal.getLastName())
		  .phone(personal.getPhone())
		  .birthDay(birth)
		  .gender(personal.isGender())
		  .build();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get successfully!")
		  .data(response)
		  .build();

	  status = HttpStatus.OK;
	} catch (CustomException | NumberFormatException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.BAD_REQUEST;
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
