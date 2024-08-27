package com.vn.topcv.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPersonalUpdateRequest {

  private String id;
  private String title;
  private String province;
  private String workType;
  private String description;
}
