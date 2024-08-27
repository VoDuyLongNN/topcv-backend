package com.vn.topcv.dto.request;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Value;

/**
 * DTO for {@link com.vn.topcv.entity.Company}
 */
@Value
public class CompanyUpdateRequest implements Serializable {

  String companyName;
  String avt;
  String industry;
  String location;
  String establishment;
  String website;
  String description;
}