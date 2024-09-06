package com.vn.topcv.controller;

import com.vn.topcv.dto.request.PostCompanyCreateRequest;
import com.vn.topcv.service.IPostCompanyService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("company")
@PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
public class PostCompanyController {

  @Autowired
  private IPostCompanyService postCompanyService;

  @PostMapping("/create")
  public ResponseEntity<ResponseObject> create(@RequestBody PostCompanyCreateRequest request) {
    return postCompanyService.create(request);
  }

  @GetMapping("/get-by-company")
  public ResponseEntity<ResponseObject> getByCompanyId() {
    return postCompanyService.getByCompany();
  }
}
