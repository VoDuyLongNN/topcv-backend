package com.vn.topcv.service.impl;

import com.vn.topcv.dto.response.DistrictGetByProvinceResponse;
import com.vn.topcv.entity.District;
import com.vn.topcv.entity.Province;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.IDistrictRepository;
import com.vn.topcv.repository.IProvinceRepository;
import com.vn.topcv.service.IDistrictService;
import com.vn.topcv.util.ResponseObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DistrictServiceImpl implements IDistrictService {

  @Autowired
  private IDistrictRepository districtRepository;

  @Autowired
  private IProvinceRepository provinceRepository;

  @Override
  public ResponseEntity<ResponseObject> findByProvince(String provinceCode) {

	ResponseObject responseObject;
	HttpStatus httpStatus;

	try {
	  Province province = provinceRepository.findByCode(provinceCode)
		  .orElseThrow(() -> new CustomException("Province Not Found!"));

	  List<District> districts = districtRepository.findByProvince(province);

	  List<DistrictGetByProvinceResponse> responses = districts.stream()
		  .map(district -> DistrictGetByProvinceResponse.builder()
			  .code(district.getCode())
			  .name(district.getName())
			  .nameEn(district.getNameEn())
			  .fullName(district.getFullName())
			  .fullNameEn(district.getFullNameEn())
			  .codeName(district.getCodeName())
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
