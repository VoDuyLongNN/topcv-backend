package com.vn.topcv.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPersonalCreateRequest {

  private String title;
  private String province;
  private String workType;
  private String jobCategoryId;
  private String jobSubCategoryId;
  private String description;
}
