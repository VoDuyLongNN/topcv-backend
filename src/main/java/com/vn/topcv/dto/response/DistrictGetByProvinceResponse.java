package com.vn.topcv.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DistrictGetByProvinceResponse {

  private String code;
  private String name;
  private String nameEn;
  private String fullName;
  private String fullNameEn;
  private String codeName;
}
