package com.vn.topcv.service;

import com.vn.topcv.dto.request.JobSubCategoryGetAllByJobCategoryRequest;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IJobSubCategoryService {

  ResponseEntity<ResponseObject> getAllByJobCategory(
      JobSubCategoryGetAllByJobCategoryRequest request);
}
