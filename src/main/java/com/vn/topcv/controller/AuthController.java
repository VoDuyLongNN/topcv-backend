package com.vn.topcv.controller;

import com.vn.topcv.dto.request.UserLoginRequest;
import com.vn.topcv.dto.request.UserLogoutRequest;
import com.vn.topcv.dto.request.UserRegisterRequest;
import com.vn.topcv.service.IAuthService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/auth")
public class AuthController {

  @Autowired
  private IAuthService userService;

  @PostMapping("register")
  public ResponseEntity<ResponseObject> register(@RequestBody UserRegisterRequest request){
    return userService.register(request);
  }

  @PostMapping("login")
  public ResponseEntity<ResponseObject> login(@RequestBody UserLoginRequest request){
    return userService.login(request);
  }

  @PostMapping("logout")
  public ResponseEntity<ResponseObject> logout(@RequestBody UserLogoutRequest request){
    return userService.logout(request);
  }
}
