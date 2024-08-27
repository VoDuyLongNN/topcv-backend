package com.vn.topcv.service;

import com.vn.topcv.dto.request.UserChangePasswordRequest;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IUserService {

	ResponseEntity<ResponseObject> changePassword(UserChangePasswordRequest request);

	ResponseEntity<ResponseObject> uploadAvt(MultipartFile file);

	ResponseEntity<ResponseObject> getAvt();
}
