package com.vn.topcv.service.impl;

import com.vn.topcv.dto.request.PostCompanyCreateRequest;
import com.vn.topcv.entity.Company;
import com.vn.topcv.entity.District;
import com.vn.topcv.entity.Experience;
import com.vn.topcv.entity.JobCategory;
import com.vn.topcv.entity.JobSubCategory;
import com.vn.topcv.entity.PostCompany;
import com.vn.topcv.entity.Province;
import com.vn.topcv.entity.Salary;
import com.vn.topcv.entity.User;
import com.vn.topcv.entity.Ward;
import com.vn.topcv.entity.enums.EPostStatus;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.ICompanyRepository;
import com.vn.topcv.repository.IDistrictRepository;
import com.vn.topcv.repository.IExperienceRepository;
import com.vn.topcv.repository.IJobCategoryRepository;
import com.vn.topcv.repository.IJobSubCategoryRepository;
import com.vn.topcv.repository.IPostCompanyRepository;
import com.vn.topcv.repository.IProvinceRepository;
import com.vn.topcv.repository.ISalaryRepository;
import com.vn.topcv.repository.IWardRepository;
import com.vn.topcv.service.IPostCompanyService;
import com.vn.topcv.util.ResponseObject;
import com.vn.topcv.validation.RequestValidation;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PostCompanyServiceImpl implements IPostCompanyService {

  @Autowired
  private IPostCompanyRepository postCompanyRepository;

  @Autowired
  private ICompanyRepository companyRepository;

  @Autowired
  private IJobCategoryRepository jobCategoryRepository;

  @Autowired
  private IJobSubCategoryRepository jobSubCategoryRepository;

  @Autowired
  private IProvinceRepository provinceRepository;

  @Autowired
  private IDistrictRepository districtRepository;

  @Autowired
  private IWardRepository wardRepository;

  @Autowired
  private ISalaryRepository salaryRepository;

  @Autowired
  private IExperienceRepository experienceRepository;

  @Override
  public ResponseEntity<ResponseObject> create(PostCompanyCreateRequest request) {
	ResponseObject responseObject;
	HttpStatus status;

	boolean isValid = true;
	String message = "";

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if (user == null) {
		throw new CustomException("User not found!");
	  }

	  Company company = companyRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("Company not found!"));

	  if (request.getTitle().isEmpty()) {
		message += "Title can't be empty!";
		isValid = false;
	  }

	  if (request.getProvince().isEmpty()) {
		message += "Province can't be empty!";
		isValid = false;
	  }

	  if (request.getDetails().isEmpty()) {
		message += "Details can't be empty!";
		isValid = false;
	  }

	  if (request.getWard().isEmpty()) {
		message += "Ward can't be empty!";
		isValid = false;
	  }

	  if (request.getLocation().isEmpty()) {
		message += "Location can't be empty!";
		isValid = false;
	  }

	  if (request.getJobCategory().isEmpty() || !RequestValidation.isValidLongNumber(
		  request.getJobCategory())) {
		message += "JobCategory can't be empty!";
		isValid = false;
	  }

	  if (request.getJobSubCategory().isEmpty() || !RequestValidation.isValidLongNumber(
		  request.getJobSubCategory())) {
		message += "JobSubCategory can't be empty!";
		isValid = false;
	  }

	  if (request.getSalary().isEmpty()) {
		message += "Salary can't be empty!";
		isValid = false;
	  }

	  if (request.getExperience().isEmpty()) {
		message += "Experience can't be empty!";
		isValid = false;
	  }

	  if (request.getQuantity().isEmpty() || !RequestValidation.isValidLongNumber(
		  request.getQuantity())) {
		message += "Quantity can't be empty or invalid number!";
		isValid = false;
	  }

	  if (request.getExpired().isEmpty() || !RequestValidation.isValidLocalDate(
		  request.getExpired())) {
		message += "Expired can't be empty or invalid date!";
		isValid = false;
	  }

	  if (request.getDetails().isEmpty()) {
		message += "Details can't be empty!";
		isValid = false;
	  }

	  if (request.getRequirements().isEmpty()) {
		message += "Requirements can't be empty!";
		isValid = false;
	  }

	  if (request.getBenefit().isEmpty()) {
		message += "Benefit can't be empty!";
		isValid = false;
	  }

	  if (request.getWorkTime().isEmpty()) {
		message += "WorkTime can't be empty!";
		isValid = false;
	  }

	  if (isValid) {
		Province province = provinceRepository.findByCode(request.getProvince())
			.orElseThrow(() -> new CustomException("Province not found!"));

		District district = districtRepository.findByCode(request.getDistrict())
			.orElseThrow(() -> new CustomException("District not found!"));

		Ward ward = wardRepository.findByCode(request.getWard())
			.orElseThrow(() -> new CustomException("Ward not found!"));

		JobCategory jobCategory = jobCategoryRepository.findById(
				Long.parseLong(request.getJobCategory()))
			.orElseThrow(() -> new CustomException("Job Category not found!"));

		JobSubCategory jobSubCategory = jobSubCategoryRepository.findById(
				Long.parseLong(request.getJobSubCategory()))
			.orElseThrow(() -> new CustomException("Job subcategory not found!"));

		Salary salary = salaryRepository.findById(Integer.parseInt(request.getSalary()))
			.orElseThrow(() -> new CustomException("Salary not found!"));

		Experience experience = experienceRepository.findById(
				Integer.parseInt(request.getExperience()))
			.orElseThrow(() -> new CustomException("Experience not found!"));

		Integer quantity = Integer.parseInt(request.getQuantity());

		if(quantity <= 0) {
		  throw new CustomException("The quantity cannot be less than zero!");
		}

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate expired = LocalDate.parse(request.getExpired(), dateFormatter);

		PostCompany postCompany = PostCompany.builder()
			.title(request.getTitle())
			.province(province)
			.district(district)
			.ward(ward)
			.location(request.getLocation())
			.jobCategory(jobCategory)
			.jobSubCategory(jobSubCategory)
			.salary(salary)
			.experience(experience)
			.quantity(quantity)
			.expired(expired)
			.details(request.getDetails())
			.requirements(request.getRequirements())
			.benefit(request.getBenefit())
			.workTime(request.getWorkTime())
			.company(company)
			.status(EPostStatus.ACTIVE)
			.createDate(new Timestamp(System.currentTimeMillis()))
			.build();

		postCompanyRepository.save(postCompany);

		responseObject = ResponseObject.builder()
			.status(HttpStatus.OK.name())
			.message("Tạo bài viết thành công!")
			.build();

		status = HttpStatus.OK;
	  } else {
		responseObject = ResponseObject.builder()
			.status(HttpStatus.BAD_REQUEST.name())
			.message(message)
			.build();

		status = HttpStatus.BAD_REQUEST;
	  }
	} catch (NumberFormatException | CustomException e) {
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
  public ResponseEntity<ResponseObject> getByCompany() {
	ResponseObject responseObject;
	HttpStatus status;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  Company company = companyRepository.findByUserId(user.getUserId()).orElseThrow(() -> new CustomException("Company not found"));

	  List<PostCompany> postCompanies = postCompanyRepository.findByCompany(company);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get successfully!")
		  .data(postCompanies)
		  .build();

	  status = HttpStatus.OK;
	} catch (CustomException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.NOT_FOUND.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.NOT_FOUND;
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
