package com.vn.topcv.dto.response;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link com.vn.topcv.entity.Company}
 */
@Value
@Builder
public class CompanyGetResponse implements Serializable {

  Long companyId;
  String companyName;
  String avt;
  String industry;
  String location;
  LocalDate establishment;
  String website;
  String description;
  Timestamp createDate;
  Timestamp updateTime;
  String email;
}