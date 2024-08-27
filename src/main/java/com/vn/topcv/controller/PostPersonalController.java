package com.vn.topcv.controller;

import com.vn.topcv.dto.request.PostPersonalCreateRequest;
import com.vn.topcv.dto.request.PostPersonalDeleteByIdRequest;
import com.vn.topcv.dto.request.PostPersonalUpdateRequest;
import com.vn.topcv.service.IPostPersonalService;
import com.vn.topcv.service.IProvinceService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("post-personal")
@PreAuthorize("hasAnyAuthority('ROLE_PERSONAL')")
public class PostPersonalController {

  @Autowired
  private IPostPersonalService postPersonalService;

  @Autowired
  IProvinceService provinceService;

  @PostMapping("create")
  public ResponseEntity<ResponseObject> create(@RequestBody PostPersonalCreateRequest request) {
	return postPersonalService.create(request);
  }

  @GetMapping("get-by-personal")
  public ResponseEntity<ResponseObject> getByPersonal(
	  @RequestParam(defaultValue = "0") int page,
	  @RequestParam(defaultValue = "10") int size,
	  @RequestParam(required = false) String province,
	  @RequestParam(required = false) String status,
	  @RequestParam(required = false) String jobType) {
	return postPersonalService.getAllByPersonal(page, size, province, status, jobType);
  }

  @DeleteMapping("delete")
  public ResponseEntity<ResponseObject> delete(@RequestBody PostPersonalDeleteByIdRequest request) {
	return postPersonalService.deleteById(request);
  }

  @GetMapping("get/{id}")
  public ResponseEntity<ResponseObject> getById(@PathVariable("id") String id) {
	return postPersonalService.getById(id);
  }

  @PutMapping("update")
  public ResponseEntity<ResponseObject> update(@RequestBody PostPersonalUpdateRequest request) {
	return postPersonalService.update(request);
  }
}
