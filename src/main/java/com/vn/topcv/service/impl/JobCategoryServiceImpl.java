package com.vn.topcv.service.impl;

import com.vn.topcv.entity.JobCategory;
import com.vn.topcv.repository.IJobCategoryRepository;
import com.vn.topcv.service.IJobCategoryService;
import com.vn.topcv.util.ResponseObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class JobCategoryServiceImpl implements IJobCategoryService {

  @Autowired
  private IJobCategoryRepository jcRepo;

  @Override
  public ResponseEntity<ResponseObject> getAll() {
	ResponseObject responseObject;
	HttpStatus status;

	try{
	  List<JobCategory> jobCategory = jcRepo.findAll();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get success fully!")
		  .data(jobCategory)
		  .build();

	  status = HttpStatus.OK;
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
