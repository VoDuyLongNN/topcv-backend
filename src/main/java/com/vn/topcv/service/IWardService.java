package com.vn.topcv.service;

import com.vn.topcv.entity.District;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IWardService {

  ResponseEntity<ResponseObject> getByDistrict(String district);
}
