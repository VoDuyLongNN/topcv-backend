package com.vn.topcv.service.impl;

import com.vn.topcv.dto.response.ProvinceGetAllResponse;
import com.vn.topcv.entity.Province;
import com.vn.topcv.repository.IProvinceRepository;
import com.vn.topcv.service.IProvinceService;
import com.vn.topcv.util.ResponseObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl implements IProvinceService {

  @Autowired
  private IProvinceRepository provinceRepository;

  @Override
  public ResponseEntity<ResponseObject> getAll() {
    ResponseObject responseObject;
    HttpStatus httpStatus;

    try{
      List<Province> provinceList = provinceRepository.findAll();

      List<ProvinceGetAllResponse> responseList = provinceList.stream()
          .map(this::convertToResponse)
          .toList();

      responseObject = ResponseObject.builder()
          .status(HttpStatus.OK.name())
          .message("Get all province successfully!")
          .data(responseList)
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

  private ProvinceGetAllResponse convertToResponse(Province province) {
    return new ProvinceGetAllResponse(
        province.getCode(),
        province.getName(),
        province.getNameEn(),
        province.getFullName(),
        province.getFullNameEn(),
        province.getCodeName()
    );
  }
}
