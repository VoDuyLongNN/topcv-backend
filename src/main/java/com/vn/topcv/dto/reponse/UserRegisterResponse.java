package com.vn.topcv.dto.reponse;

import com.vn.topcv.entity.Role;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterResponse {

  private String email;
  private String password;
  private Set<Role> roles;
}
