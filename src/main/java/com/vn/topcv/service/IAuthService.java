package com.vn.topcv.service;

import com.vn.topcv.dto.request.UserLogoutRequest;
import com.vn.topcv.dto.request.UserRegisterRequest;
import com.vn.topcv.dto.request.UserLoginRequest;
import com.vn.topcv.util.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
	ResponseEntity<ResponseObject> register(UserRegisterRequest request);

	ResponseEntity<ResponseObject> login(UserLoginRequest request);

	ResponseEntity<ResponseObject> logout(UserLogoutRequest request);
}
