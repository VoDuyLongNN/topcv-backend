package com.vn.topcv.dto.request;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

/**
 * DTO for {@link com.vn.topcv.entity.PostCompany}
 */
@Value
@Data
@Builder
@AllArgsConstructor
public class PostCompanyCreateRequest implements Serializable {

  String title;
  String expired;
  String details;
  String quantity;
  String requirements;
  String benefit;
  String location;
  String workTime;
  String salary;
  String experience;
  String jobCategory;
  String jobSubCategory;
  String province;
  String district;
  String ward;
}