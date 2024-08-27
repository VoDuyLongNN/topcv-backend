package com.vn.topcv.controller;

import com.vn.topcv.dto.request.PersonalUpdateRequest;
import com.vn.topcv.service.IPersonalService;
import com.vn.topcv.service.IUserService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personal")
public class PersonalController {

  @Autowired
  private IUserService userService;

  @Autowired
  IPersonalService personalService;

  @GetMapping()
  @PreAuthorize("hasAnyAuthority('ROLE_PERSONAL')")
  public ResponseEntity<ResponseObject> getPersonal() {
	return personalService.getCurrentPersonal();
  }

  @PutMapping()
  @PreAuthorize("hasAnyAuthority('ROLE_PERSONAL')")
  public ResponseEntity<ResponseObject> updatePersonal(@RequestBody PersonalUpdateRequest request){
    return personalService.updatePersonalUser(request);
  }
}
