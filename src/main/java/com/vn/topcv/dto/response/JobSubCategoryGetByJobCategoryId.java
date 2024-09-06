package com.vn.topcv.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link com.vn.topcv.entity.JobSubCategory}
 */
@Value
@Builder
public class JobSubCategoryGetByJobCategoryId implements Serializable {

  String id;
  String name;
}