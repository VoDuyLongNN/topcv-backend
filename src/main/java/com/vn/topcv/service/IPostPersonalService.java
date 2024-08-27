package com.vn.topcv.service;

import com.vn.topcv.dto.request.PostPersonalCreateRequest;
import com.vn.topcv.dto.request.PostPersonalDeleteByIdRequest;
import com.vn.topcv.dto.request.PostPersonalGetByIdRequest;
import com.vn.topcv.dto.request.PostPersonalUpdateRequest;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IPostPersonalService {

  ResponseEntity<ResponseObject> create(PostPersonalCreateRequest request);

  ResponseEntity<ResponseObject> getAllByPersonal(int page, int size, String province, String status, String jobType);

  ResponseEntity<ResponseObject> deleteById(PostPersonalDeleteByIdRequest request);

  ResponseEntity<ResponseObject> getById(String Id);

  ResponseEntity<ResponseObject> update(PostPersonalUpdateRequest request);

  ResponseEntity<ResponseObject> getAllActivePost(int page, int size, String sortDir, String province, String jobType, String searchQuery);

  ResponseEntity<ResponseObject> getNumberOfTotalActivePost();

  ResponseEntity<ResponseObject> getPostById(PostPersonalGetByIdRequest request);
}
