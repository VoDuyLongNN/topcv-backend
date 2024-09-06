package com.vn.topcv.service.impl;

import com.vn.topcv.dto.response.PostPersonalGetAllResponse;
import com.vn.topcv.dto.request.PostPersonalCreateRequest;
import com.vn.topcv.dto.request.PostPersonalDeleteByIdRequest;
import com.vn.topcv.dto.request.PostPersonalGetByIdRequest;
import com.vn.topcv.dto.request.PostPersonalUpdateRequest;
import com.vn.topcv.entity.JobCategory;
import com.vn.topcv.entity.JobSubCategory;
import com.vn.topcv.entity.Personal;
import com.vn.topcv.entity.PostPersonal;
import com.vn.topcv.entity.Province;
import com.vn.topcv.entity.User;
import com.vn.topcv.entity.enums.EPostStatus;
import com.vn.topcv.entity.enums.EWorkType;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.IJobCategoryRepository;
import com.vn.topcv.repository.IJobSubCategoryRepository;
import com.vn.topcv.repository.IPersonalRepository;
import com.vn.topcv.repository.IPostPersonalRepository;
import com.vn.topcv.repository.IProvinceRepository;
import com.vn.topcv.service.IPostPersonalService;
import com.vn.topcv.util.PostPersonalSpecification;
import com.vn.topcv.util.ResponseObject;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PostPersonalServiceImpl implements IPostPersonalService {

  @Autowired
  private IPostPersonalRepository postPersonalRepository;

  @Autowired
  private IPersonalRepository personalRepository;

  @Autowired
  private IJobCategoryRepository jobCategoryRepository;

  @Autowired
  private IJobSubCategoryRepository jobSubCategoryRepository;

  @Autowired
  private IProvinceRepository provinceRepository;

  @Override
  public ResponseEntity<ResponseObject> create(PostPersonalCreateRequest request) {
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

	  Province province = provinceRepository.findByCode(request.getProvince())
		  .orElseThrow(() -> new CustomException("Province not found!"));

	  if (request.getTitle().isEmpty()) {
		message += "Title can't be empty!. ";
		isValid = false;
	  }

	  if (request.getProvince().isEmpty()) {
		message += "Province can't be empty!. ";
		isValid = false;
	  }

	  if (request.getWorkType().isEmpty()) {
		message += "WorkType can't be empty!. ";
		isValid = false;
	  }

	  if (request.getDescription().isEmpty()) {
		message += "Description can't be empty!. ";
		isValid = false;
	  }

	  if (request.getJobCategoryId().isEmpty()) {
		message += "JobCategoryId can't be empty!. ";
		isValid = false;
	  }

	  if (request.getJobSubCategoryId().isEmpty()) {
		message += "JobSubCategoryId can't be empty!. ";
		isValid = false;
	  }

	  if (isValid) {
		EWorkType eWorkType;

		if (request.getWorkType().equals("FULL_TIME")) {
		  eWorkType = EWorkType.FULL_TIME;
		} else if (request.getWorkType().equals("PART_TIME")) {
		  eWorkType = EWorkType.PART_TIME;
		} else {
		  throw new CustomException("Invalid work type!");
		}

		JobCategory jobCategory = jobCategoryRepository.findById(
				Long.parseLong(request.getJobCategoryId()))
			.orElseThrow(() -> new CustomException("JobCategory not found!"));
		JobSubCategory jobSubCategory = jobSubCategoryRepository.findById(
				Long.parseLong(request.getJobSubCategoryId()))
			.orElseThrow(() -> new CustomException("JobSubCategory not found!"));

		PostPersonal postPersonal = PostPersonal.builder()
			.title(request.getTitle())
			.province(province)
			.workType(eWorkType)
			.description(request.getDescription())
			.status(EPostStatus.ACTIVE)
			.jobCategory(jobCategory)
			.jobSubCategory(jobSubCategory)
			.personal(personal)
			.createDate(new Timestamp(System.currentTimeMillis()))
			.build();

		postPersonalRepository.save(postPersonal);

		responseObject = ResponseObject.builder()
			.status(HttpStatus.OK.name())
			.message("Bài đăng của bạn đã được lưu, chờ quản trị viên duyệt!")
			.data(postPersonal)
			.build();

		status = HttpStatus.CREATED;
	  } else {
		responseObject = ResponseObject.builder()
			.status(HttpStatus.BAD_REQUEST.name())
			.message(message)
			.build();

		status = HttpStatus.BAD_REQUEST;
	  }
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

  @Override
  public ResponseEntity<ResponseObject> getAllByPersonal(int page, int size, String province,
	  String status, String jobType) {
	ResponseObject responseObject;
	HttpStatus httpStatus;
	PostPersonalGetAllResponse response;
	EPostStatus ePostStatus = null;

	if (Objects.equals(status, "ACTIVE")) {
	  ePostStatus = EPostStatus.ACTIVE;
	} else if (Objects.equals(status, "UNACTIVE")) {
	  ePostStatus = EPostStatus.UNACTIVE;
	}

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Personal personal = personalRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  Pageable pageable = PageRequest.of(page, size);

	  Specification<PostPersonal> spec = Specification.where(
			  PostPersonalSpecification.hasProvince(province))
		  .and(PostPersonalSpecification.hasStatus(ePostStatus))
		  .and(PostPersonalSpecification.hasJobType(jobType))
		  .and((root, query, criteriaBuilder) ->
			  criteriaBuilder.equal(root.get("personal"), personal)
		  );

	  Page<PostPersonal> postPersonals = postPersonalRepository.findAll(spec, pageable);

	  List<PostPersonalGetAllResponse> responses = postPersonals.getContent().stream()
		  .map(post -> {
			Long numberSaved = postPersonalRepository.countCompanySavedPost(post.getId());
			return PostPersonalGetAllResponse.builder()
				.id(post.getId().toString())
				.title(post.getTitle())
				.workType(post.getWorkType())
				.description(post.getDescription())
				.status(post.getStatus())
				.createdAt(post.getCreateDate())
				.updatedAt(post.getUpdateTime())
				.province(post.getProvince().getName())
				.jobType(post.getJobCategory().getType())
				.jobName(post.getJobSubCategory().getName())
				.phone(personal.getPhone())
				.email(user.getEmail())
				.numberSaved(numberSaved.toString())
				.build();
		  }).toList();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get all post personals successfully!")
		  .data(responses)
		  .currentPage(postPersonals.getNumber())
		  .totalItems(postPersonals.getTotalElements())
		  .totalPages(postPersonals.getTotalPages())
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
  public ResponseEntity<ResponseObject> deleteById(PostPersonalDeleteByIdRequest request) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Personal personal = personalRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  if (request.getId().isEmpty()) {
		throw new CustomException("Id cannot be empty!");
	  }

	  Long id = Long.parseLong(request.getId());

	  PostPersonal postPersonal = postPersonalRepository.findById(id)
		  .orElseThrow(() -> new CustomException("Post ID not found!"));
	  postPersonalRepository.delete(postPersonal);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Post deleted!")
		  .data(postPersonal)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (NumberFormatException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message("Invalid Post Id!")
		  .build();

	  httpStatus = HttpStatus.BAD_REQUEST;
	} catch (CustomException e) {
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

  @Override
  public ResponseEntity<ResponseObject> getById(String id) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Personal personal = personalRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  if (id == null) {
		throw new CustomException("Id cannot be empty!");
	  }

	  Long longId = Long.parseLong(id);

	  PostPersonal postPersonal = postPersonalRepository.findById(longId)
		  .orElseThrow(() -> new CustomException("Post ID not found!"));

	  PostPersonalGetAllResponse response = PostPersonalGetAllResponse.builder()
		  .id(postPersonal.getId().toString())
		  .title(postPersonal.getTitle())
		  .workType(postPersonal.getWorkType())
		  .description(postPersonal.getDescription())
		  .status(postPersonal.getStatus())
		  .createdAt(postPersonal.getCreateDate())
		  .updatedAt(postPersonal.getUpdateTime())
		  .province(postPersonal.getProvince().getCode())
		  .jobType(postPersonal.getJobCategory().getType())
		  .jobName(postPersonal.getJobSubCategory().getName())
		  .phone(postPersonal.getPersonal().getPhone())
		  .email(user.getEmail())
		  .build();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get post personal successfully!")
		  .data(response)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (NumberFormatException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.BAD_REQUEST;
	} catch (CustomException e) {
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

  @Override
  public ResponseEntity<ResponseObject> update(PostPersonalUpdateRequest request) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Personal personal = personalRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("User not found!"));

	  Long longId = Long.parseLong(request.getId());

	  PostPersonal post = postPersonalRepository.findById(longId)
		  .orElseThrow(() -> new CustomException("Post ID not found!"));

	  if (!request.getTitle().isEmpty()) {
		post.setTitle(request.getTitle());
	  }

	  if (!request.getProvince().isEmpty()) {
		Province province = provinceRepository.findByCode(request.getProvince())
			.orElseThrow(() -> new CustomException("Province not found!"));
		post.setProvince(province);
	  }

	  if (!request.getWorkType().isEmpty()) {
		EWorkType eWorkType;

		if (request.getWorkType().equals("FULL_TIME")) {
		  eWorkType = EWorkType.FULL_TIME;
		} else if (request.getWorkType().equals("PART_TIME")) {
		  eWorkType = EWorkType.PART_TIME;
		} else {
		  throw new CustomException("Invalid work type!");
		}

		post.setWorkType(eWorkType);
	  }

	  if (!request.getDescription().isEmpty()) {
		post.setDescription(request.getDescription());
	  }

	  post.setUpdateTime(new Timestamp(System.currentTimeMillis()));

	  postPersonalRepository.save(post);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Update post successfully!")
		  .data(post)
		  .build();

	  httpStatus = HttpStatus.OK;
	} catch (NumberFormatException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message(e.getMessage())
		  .build();

	  httpStatus = HttpStatus.BAD_REQUEST;
	} catch (CustomException e) {
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

  @Override
  public ResponseEntity<ResponseObject> getAllActivePost(int page, int size,
	  String sortDir, String province, String jobType, String searchQuery) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  Sort sort = sortDir.equalsIgnoreCase(Direction.ASC.name()) ? Sort.by("createDate").ascending()
		  : Sort.by("createDate").descending();

	  Pageable pageable = PageRequest.of(page, size, sort);

	  Specification<PostPersonal> spec = Specification.where(
			  PostPersonalSpecification.hasProvince(province))
		  .and(PostPersonalSpecification.hasStatus(EPostStatus.ACTIVE))
		  .and(PostPersonalSpecification.hasJobType(jobType))
		  .and(PostPersonalSpecification.hasSearchQuery(searchQuery));

	  Page<PostPersonal> postPersonals = postPersonalRepository.findAll(spec, pageable);

	  List<PostPersonalGetAllResponse> responses = postPersonals.getContent().stream()
		  .map(post -> PostPersonalGetAllResponse.builder()
			  .id(post.getId().toString())
			  .title(post.getTitle())
			  .workType(post.getWorkType())
			  .description(post.getDescription())
			  .status(post.getStatus())
			  .createdAt(post.getCreateDate())
			  .updatedAt(post.getUpdateTime())
			  .province(post.getProvince().getName())
			  .jobType(post.getJobCategory().getType())
			  .jobName(post.getJobSubCategory().getName())
			  .email(post.getPersonal().getUser().getEmail())
			  .phone(post.getPersonal().getPhone())
			  .build()
		  ).toList();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("get all post successfully!")
		  .data(responses)
		  .currentPage(postPersonals.getNumber())
		  .totalItems(postPersonals.getTotalElements())
		  .totalPages(postPersonals.getTotalPages())
		  .build();

	  httpStatus = HttpStatus.OK;
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
  public ResponseEntity<ResponseObject> getNumberOfTotalActivePost() {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  List<PostPersonal> postPersonals = postPersonalRepository.getPostPersonalByStatus(
		  EPostStatus.ACTIVE);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("get all post successfully!")
		  .data(postPersonals.size())
		  .build();

	  httpStatus = HttpStatus.OK;
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
  public ResponseEntity<ResponseObject> getPostById(PostPersonalGetByIdRequest request) {
	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  Long postId = Long.parseLong(request.getId());

	  PostPersonal postPersonal = postPersonalRepository.findById(postId)
		  .orElseThrow(() -> new CustomException("Post not found!"));

	  if (postPersonal.getStatus() != EPostStatus.ACTIVE) {
		throw new CustomException("Post invalid!");
	  }

	  PostPersonalGetAllResponse response = PostPersonalGetAllResponse.builder()
		  .id(postPersonal.getId().toString())
		  .status(postPersonal.getStatus())
		  .phone(postPersonal.getPersonal().getPhone())
		  .province(postPersonal.getProvince().getName())
		  .title(postPersonal.getTitle())
		  .description(postPersonal.getDescription())
		  .jobType(postPersonal.getJobCategory().getType())
		  .jobName(postPersonal.getJobSubCategory().getName())
		  .email(postPersonal.getPersonal().getUser().getEmail())
		  .workType(postPersonal.getWorkType())
		  .createdAt(postPersonal.getCreateDate())
		  .updatedAt(postPersonal.getUpdateTime())
		  .build();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get post successfully!")
		  .data(response)
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
}
