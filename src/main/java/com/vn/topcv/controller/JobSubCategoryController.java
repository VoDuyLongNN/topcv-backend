package com.vn.topcv.controller;

import com.vn.topcv.dto.request.JobSubCategoryGetAllByJobCategoryRequest;
import com.vn.topcv.service.IJobSubCategoryService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job-subcategory")
public class JobSubCategoryController {

  @Autowired
  private IJobSubCategoryService jobSubCategoryService;

  @PostMapping("get-all")
  public ResponseEntity<ResponseObject> getAllByJobCategory(@RequestBody
      JobSubCategoryGetAllByJobCategoryRequest request){
    return jobSubCategoryService.getAllByJobCategory(request);
  }
}
