package com.vn.topcv.controller;

import com.vn.topcv.dto.request.CompanySavePostRequest;
import com.vn.topcv.dto.request.CompanyUpdateRequest;
import com.vn.topcv.service.ICompanyService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("company")
@PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
public class CompanyController {

  @Autowired
  private ICompanyService companyService;

  @GetMapping("get")
  public ResponseEntity<ResponseObject> getCurrentCompany() {
    return companyService.getCurrentCompany();
  }

  @PostMapping("save-post")
  public ResponseEntity<ResponseObject> savePost(@RequestBody CompanySavePostRequest request) {
    return companyService.savePostForCompany(request);
  }

  @PostMapping("un-save-post")
  public ResponseEntity<ResponseObject> unSavePost(@RequestBody CompanySavePostRequest request) {
    return companyService.unSavePostForCompany(request);
  }

  @GetMapping("get-saved-post")
  public ResponseEntity<ResponseObject> getSavedPost() {
    return companyService.getSavedPost();
  }

  @PutMapping("update")
  public ResponseEntity<ResponseObject> update(@RequestBody CompanyUpdateRequest request) {
    return companyService.update(request);
  }
}
