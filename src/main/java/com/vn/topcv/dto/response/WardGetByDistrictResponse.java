package com.vn.topcv.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link com.vn.topcv.entity.Ward}
 */
@Value
@Builder
public class WardGetByDistrictResponse implements Serializable {

  @Size(max = 20)
  String code;
  @NotNull
  @Size(max = 255)
  String name;
  @Size(max = 255)
  String nameEn;
  @Size(max = 255)
  String fullName;
  @Size(max = 255)
  String fullNameEn;
  @Size(max = 255)
  String codeName;
}