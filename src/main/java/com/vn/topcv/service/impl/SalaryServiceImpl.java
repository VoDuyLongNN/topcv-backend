package com.vn.topcv.service.impl;

import com.vn.topcv.dto.response.SalaryGetAllResponse;
import com.vn.topcv.dto.response.WardGetByDistrictResponse;
import com.vn.topcv.entity.Salary;
import com.vn.topcv.repository.ISalaryRepository;
import com.vn.topcv.service.ISalaryService;
import com.vn.topcv.util.ResponseObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl implements ISalaryService {

  @Autowired
  private ISalaryRepository salaryRepository;

  @Override
  public ResponseEntity<ResponseObject> getAll() {

	ResponseObject responseObject;
	HttpStatus httpStatus;

	try{
	  List<Salary> salaryList = salaryRepository.findAll();

	  List<SalaryGetAllResponse> responses = salaryList.stream()
		  .map(salary -> SalaryGetAllResponse.builder()
			  .id(salary.getId().toString())
			  .name(salary.getName())
			  .build())
		  .toList();


	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get successfully!")
		  .data(responses)
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
}
