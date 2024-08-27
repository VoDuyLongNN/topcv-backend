package com.vn.topcv.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalGetByPostIdResponse {

  String personalId;
  String avtUrl;
  String email;
  String firstName;
  String lastName;
  String phone;
  String birthDay;
  boolean gender;
}
