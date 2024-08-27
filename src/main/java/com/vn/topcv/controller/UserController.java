package com.vn.topcv.controller;

import com.vn.topcv.dto.request.UserChangePasswordRequest;
import com.vn.topcv.service.IUserService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("user")
public class UserController {

  @Autowired
  private IUserService userService;

  @PutMapping("change-password")
  @PreAuthorize("hasAnyAuthority('ROLE_COMPANY', 'ROLE_PERSONAL')")
  public ResponseEntity<ResponseObject> changePassword(@RequestBody UserChangePasswordRequest request) {
    return userService.changePassword(request);
  }

  @PostMapping("upload-avt")
  @PreAuthorize("hasAnyAuthority('ROLE_COMPANY', 'ROLE_PERSONAL')")
  public ResponseEntity<ResponseObject> uploadAvt(@RequestParam("file") MultipartFile file) {
    return userService.uploadAvt(file);
  }

  @GetMapping("get-avt")
  @PreAuthorize("hasAnyAuthority('ROLE_COMPANY', 'ROLE_PERSONAL')")
  public ResponseEntity<ResponseObject> getAvt() {
    return userService.getAvt();
  }
}
