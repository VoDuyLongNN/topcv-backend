package com.vn.topcv.controller;

import com.vn.topcv.service.IProvinceService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("province")
public class ProvinceController {

  @Autowired
  private IProvinceService provinceService;

  @GetMapping("get-all")
  public ResponseEntity<ResponseObject> getAll() {
    return provinceService.getAll();
  }
}
