package com.vn.topcv.service.impl;

import com.vn.topcv.dto.response.ExperienceGetAllResponse;
import com.vn.topcv.dto.response.SalaryGetAllResponse;
import com.vn.topcv.entity.Experience;
import com.vn.topcv.repository.IExperienceRepository;
import com.vn.topcv.service.IExperienceService;
import com.vn.topcv.util.ResponseObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExperienceServiceImpl implements IExperienceService {

  @Autowired
  private IExperienceRepository experienceRepository;

  @Override
  public ResponseEntity<ResponseObject> getAll() {
	ResponseObject responseObject;
	HttpStatus status;

	try {
	  List<Experience> experiences = experienceRepository.findAll();

	  List<ExperienceGetAllResponse> responses = experiences.stream()
		  .map(experience -> ExperienceGetAllResponse.builder()
			  .id(experience.getId().toString())
			  .name(experience.getName())
			  .build())
		  .toList();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get all successfully!")
		  .data(responses)
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
