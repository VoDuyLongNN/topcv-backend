package com.vn.topcv.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

/**
 * DTO for {@link com.vn.topcv.entity.Salary}
 */
@Value
@Builder
public class SalaryGetAllResponse implements Serializable {

  String id;
  String name;
}