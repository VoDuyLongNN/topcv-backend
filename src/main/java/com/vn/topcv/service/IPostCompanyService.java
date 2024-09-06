package com.vn.topcv.service;

import com.vn.topcv.dto.request.PostCompanyCreateRequest;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IPostCompanyService {

  ResponseEntity<ResponseObject> create(PostCompanyCreateRequest request);

  ResponseEntity<ResponseObject> getByCompany();
}
