package com.vn.topcv.service;

import com.vn.topcv.dto.request.PersonalUpdateRequest;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IPersonalService {

  ResponseEntity<ResponseObject> getCurrentPersonal();

  ResponseEntity<ResponseObject> updatePersonalUser(PersonalUpdateRequest request);

  ResponseEntity<ResponseObject> getPersonalByPostId(String id);
}
