package com.vn.topcv.service.impl;

import com.vn.topcv.dto.response.CompanyGetResponse;
import com.vn.topcv.dto.request.CompanySavePostRequest;
import com.vn.topcv.dto.request.CompanyUpdateRequest;
import com.vn.topcv.entity.Company;
import com.vn.topcv.entity.JobCategory;
import com.vn.topcv.entity.PostPersonal;
import com.vn.topcv.entity.User;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.ICompanyRepository;
import com.vn.topcv.repository.IJobCategoryRepository;
import com.vn.topcv.repository.IPostPersonalRepository;
import com.vn.topcv.service.ICompanyService;
import com.vn.topcv.util.ResponseObject;
import com.vn.topcv.validation.RequestValidation;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyServiceImpl implements ICompanyService {

  @Autowired
  private ICompanyRepository companyRepository;

  @Autowired
  private IPostPersonalRepository postPersonalRepository;

  @Autowired
  private IJobCategoryRepository jobCategoryRepository;


  @Override
  public ResponseEntity<ResponseObject> getCurrentCompany() {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not logged in");
	  }

	  Company company = companyRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found"));

	  Long industry =
		  (company.getJobCategory() != null) ? company.getJobCategory().getId() : null;

	  assert industry != null;
	  CompanyGetResponse response = CompanyGetResponse.builder()
		  .companyId(company.getCompanyId())
		  .companyName(company.getCompanyName())
		  .avt(company.getAvt())
		  .description(company.getDescription())
		  .industry(industry.toString())
		  .location(company.getLocation())
		  .establishment(company.getEstablishment())
		  .website(company.getWebsite())
		  .updateTime(company.getUpdateTime())
		  .updateTime(company.getUpdateTime())
		  .email(company.getUser().getEmail())
		  .build();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get successfully!")
		  .data(response)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (CustomException | AssertionError e) {
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
  public ResponseEntity<ResponseObject> savePostForCompany(CompanySavePostRequest request) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {

	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Company company = companyRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  if (request.getPostId().isEmpty()) {
		throw new CustomException("Post id can't be empty");
	  }

	  Long postId = Long.parseLong(request.getPostId());

	  Optional<PostPersonal> postOptional = postPersonalRepository.findById(postId);

	  if (postOptional.isEmpty()) {
		throw new CustomException("Post not found");
	  }

	  PostPersonal postPersonal = postOptional.get();

	  if (company.getPostPersonals().stream()
		  .anyMatch(post -> post.getId().equals(postPersonal.getId()))) {
		throw new CustomException("The company has already saved this post");
	  }

	  company.getPostPersonals().add(postPersonal);
	  companyRepository.save(company);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Post saved successfully!")
		  .data(company)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (CustomException | NumberFormatException e) {
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
  @Transactional
  public ResponseEntity<ResponseObject> unSavePostForCompany(CompanySavePostRequest request) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {

	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Company company = companyRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  if (request.getPostId().isEmpty()) {
		throw new CustomException("Post id can't be empty");
	  }

	  Long postId = Long.parseLong(request.getPostId());

	  Optional<PostPersonal> postOptional = postPersonalRepository.findById(postId);

	  if (postOptional.isEmpty()) {
		throw new CustomException("Post not found");
	  }

	  PostPersonal postPersonal = postOptional.get();

	  if (company.getPostPersonals().stream()
		  .noneMatch(post -> post.getId().equals(postPersonal.getId()))) {
		throw new CustomException("The company has not saved this post!");
	  }

	  company.getPostPersonals().remove(postPersonal);
	  companyRepository.save(company);
	  companyRepository.flush();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Post unsaved successfully!")
		  .data(company)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (CustomException | NumberFormatException e) {
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
  public ResponseEntity<ResponseObject> getSavedPost() {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Company company = companyRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  List<Long> postSavedId = company.getPostPersonals().stream()
		  .map(PostPersonal::getId)
		  .toList();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get successfully!")
		  .data(postSavedId)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (CustomException e) {
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
  public ResponseEntity<ResponseObject> update(CompanyUpdateRequest request) {
	ResponseObject responseObject;
	HttpStatus httpStatus;
	String message = "";
	boolean isValid = true;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not logged in!");
	  }

	  Company company = companyRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("Company not found!"));

	  if (!RequestValidation.isValidName(request.getCompanyName())) {
		message += "Name is invalid!. ";
		isValid = false;
	  }

	  if (request.getIndustry().isEmpty()) {
		message += "Industry is invalid!. ";
		isValid = false;
	  }

	  if (!RequestValidation.isValidLongNumber(request.getIndustry())) {
		message += "Industry is invalid!. ";
		isValid = false;
	  }

	  if (request.getLocation().isEmpty()) {
		message += "Location is invalid!. ";
		isValid = false;
	  }

	  if (!RequestValidation.isValidAndNotFutureLocalDate(request.getEstablishment())) {
		message += "Establishment is invalid!. ";
		isValid = false;
	  }

	  if (isValid) {
		JobCategory jobCategory = jobCategoryRepository.findById(
				Long.parseLong(request.getIndustry()))
			.orElseThrow(() -> new CustomException("Invalid industry!"));

		company.setCompanyName(request.getCompanyName());
		company.setJobCategory(jobCategory);
		company.setLocation(request.getLocation());
		company.setWebsite(request.getWebsite());
		company.setDescription(request.getDescription());
		company.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		company.setEstablishment(LocalDate.parse(request.getEstablishment(), dateFormatter));

		companyRepository.save(company);

		responseObject = ResponseObject.builder()
			.status(HttpStatus.OK.name())
			.message("Update successfully!")
			.data(company)
			.build();

		httpStatus = HttpStatus.OK;
	  } else {
		throw new CustomException(message);
	  }
	} catch (CustomException e) {
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
}
