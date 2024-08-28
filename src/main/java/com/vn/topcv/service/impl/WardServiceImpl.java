package com.vn.topcv.service.impl;

import com.vn.topcv.dto.response.WardGetByDistrictResponse;
import com.vn.topcv.entity.District;
import com.vn.topcv.entity.Ward;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.IDistrictRepository;
import com.vn.topcv.repository.IWardRepository;
import com.vn.topcv.service.IWardService;
import com.vn.topcv.util.ResponseObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WardServiceImpl implements IWardService {

  @Autowired
  private IDistrictRepository districtRepository;

  @Autowired
  private IWardRepository wardRepository;

  @Override
  public ResponseEntity<ResponseObject> getByDistrict(String request) {

	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  District district = districtRepository.findByCode(request)
		  .orElseThrow(() -> new CustomException("District Not Found!"));

	  List<Ward> wards = wardRepository.findByDistrict(district);

	  List<WardGetByDistrictResponse> responses = wards.stream()
		  .map(ward -> WardGetByDistrictResponse.builder()
			  .code(ward.getCode())
			  .name(ward.getName())
			  .nameEn(ward.getNameEn())
			  .fullName(ward.getFullName())
			  .fullNameEn(ward.getFullNameEn())
			  .codeName(ward.getCodeName())
			  .build())
		  .toList();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get successfully!")
		  .data(responses)
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
}
