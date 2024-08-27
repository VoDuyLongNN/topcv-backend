package com.vn.topcv.service;

import com.vn.topcv.dto.request.CompanySavePostRequest;
import com.vn.topcv.dto.request.CompanyUpdateRequest;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface ICompanyService {

  ResponseEntity<ResponseObject> getCurrentCompany();

  ResponseEntity<ResponseObject> savePostForCompany(CompanySavePostRequest request);

  ResponseEntity<ResponseObject> unSavePostForCompany(CompanySavePostRequest request);

  ResponseEntity<ResponseObject> getSavedPost();

  ResponseEntity<ResponseObject> update(CompanyUpdateRequest request);

}
