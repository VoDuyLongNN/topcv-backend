package com.vn.topcv.service;

import com.vn.topcv.entity.Province;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IDistrictService {

  ResponseEntity<ResponseObject> findByProvince(String provinceCode);
}
