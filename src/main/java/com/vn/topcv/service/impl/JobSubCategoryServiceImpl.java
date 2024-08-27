package com.vn.topcv.service.impl;

import com.vn.topcv.dto.request.JobSubCategoryGetAllByJobCategoryRequest;
import com.vn.topcv.entity.JobCategory;
import com.vn.topcv.entity.JobSubCategory;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.IJobCategoryRepository;
import com.vn.topcv.repository.IJobSubCategoryRepository;
import com.vn.topcv.service.IJobSubCategoryService;
import com.vn.topcv.util.ResponseObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JobSubCategoryServiceImpl implements IJobSubCategoryService {

  @Autowired
  private IJobSubCategoryRepository jobSubCategoryRepository;

  @Autowired
  private IJobCategoryRepository jobCategoryRepository;

  @Override
  public ResponseEntity<ResponseObject> getAllByJobCategory(
	  JobSubCategoryGetAllByJobCategoryRequest request) {
	ResponseObject responseObject;
	HttpStatus status;

	try {
	  if(request.getJobCategoryId().isEmpty()){
		throw new CustomException("Job category id is empty!");
	  }

	  JobCategory jobCategory = jobCategoryRepository.findById(
			  Long.parseLong(request.getJobCategoryId()))
		  .orElseThrow(() -> new CustomException("Job Category not found!"));

	  List<JobSubCategory> jobSubCategoryList = jobSubCategoryRepository.findAllByJobCategory(
		  jobCategory);

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get success fully!")
		  .data(jobSubCategoryList)
		  .build();

	  status = HttpStatus.OK;
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
}
