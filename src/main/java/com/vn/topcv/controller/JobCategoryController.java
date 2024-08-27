package com.vn.topcv.controller;

import com.vn.topcv.service.IJobCategoryService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job-category")
public class JobCategoryController {

  @Autowired
  private IJobCategoryService jobCategoryService;

  @GetMapping("get-all")
  private ResponseEntity<ResponseObject> getAll(){
    return jobCategoryService.getAll();
  }
}
