package com.vn.topcv.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link com.vn.topcv.entity.JobCategory}
 */
@Value
@Builder
public class JobCategoryGetAllResponse implements Serializable {

  String id;
  String type;
}