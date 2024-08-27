package com.vn.topcv.service;

import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IJobCategoryService {

  ResponseEntity<ResponseObject> getAll();
}
