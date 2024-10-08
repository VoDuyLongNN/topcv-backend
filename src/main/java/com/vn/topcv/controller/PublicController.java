package com.vn.topcv.controller;

import com.vn.topcv.dto.request.PostPersonalGetByIdRequest;
import com.vn.topcv.service.IDistrictService;
import com.vn.topcv.service.IExperienceService;
import com.vn.topcv.service.IPersonalService;
import com.vn.topcv.service.IPostPersonalService;
import com.vn.topcv.service.ISalaryService;
import com.vn.topcv.service.IWardService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {

  @Autowired
  private IPostPersonalService postPersonalService;

  @Autowired
  private IPersonalService personalService;

  @Autowired
  private IDistrictService districtService;

  @Autowired
  private IWardService wardService;

  @Autowired
  private ISalaryService salaryService;

  @Autowired
  private IExperienceService experienceService;

  @GetMapping("get-all-active")
  public ResponseEntity<ResponseObject> getAllActive(
	  @RequestParam(defaultValue = "0") int page,
	  @RequestParam(defaultValue = "10") int size,
	  @RequestParam(defaultValue = "desc") String sortDir,
	  @RequestParam(required = false) String province,
	  @RequestParam(required = false) String jobType,
	  @RequestParam(required = false) String searchQuery) {
	return postPersonalService.getAllActivePost(page, size, sortDir, province, jobType,
		searchQuery);
  }

  @GetMapping("get-total-personal-post")
  public ResponseEntity<ResponseObject> getNumberOfTotalActivePersonalPost() {
	return postPersonalService.getNumberOfTotalActivePost();
  }

  @PostMapping("personal-get-post-by-id")
  public ResponseEntity<ResponseObject> getPersonalPostById(@RequestBody PostPersonalGetByIdRequest request){
	return postPersonalService.getPostById(request);
  }

  @GetMapping("get-personal-by-post-id/{postId}")
  public ResponseEntity<ResponseObject> getPersonalPostById(@PathVariable String postId) {
	return personalService.getPersonalByPostId(postId);
  }

  @GetMapping("district/get-by-province/{code}")
  public ResponseEntity<ResponseObject> getDistrictByProvince(@PathVariable String code) {
	return districtService.findByProvince(code);
  }

  @GetMapping("ward/get-by-district/{code}")
  public ResponseEntity<ResponseObject> getWardByDistrict(@PathVariable String code) {
	return wardService.getByDistrict(code);
  }

  @GetMapping("salary/get-all")
  public ResponseEntity<ResponseObject> getAllSalary(){
	return salaryService.getAll();
  }

  @GetMapping("experience/get-all")
  public ResponseEntity<ResponseObject> getAllExperience(){
	return experienceService.getAll();
  }
}
