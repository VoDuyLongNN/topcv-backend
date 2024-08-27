package com.vn.topcv.dto.reponse;

import com.vn.topcv.entity.enums.EPostStatus;
import com.vn.topcv.entity.enums.EWorkType;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPersonalGetAllResponse {

  private String id;
  private String title;
  private EWorkType workType;
  private String description;
  private EPostStatus status;
  private Timestamp createdAt;
  private Timestamp updatedAt;
  private String province;
  private String jobType;
  private String jobName;
  private String phone;
  private String email;
  private String numberSaved;
}
