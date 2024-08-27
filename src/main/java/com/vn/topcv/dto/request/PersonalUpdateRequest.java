package com.vn.topcv.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalUpdateRequest {

  private String firstName;
  private String lastName;
  private String address;
  private String location;
  private String education;
  private String gender;
  private String phone;
  private String skill;
  private String desc;
  private String birthDay;
}
