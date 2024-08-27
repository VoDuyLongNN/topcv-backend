package com.vn.topcv.dto.reponse;

import com.vn.topcv.entity.User;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginResponse {

  private User user;
  private String token;
  private String refreshToken;
  private Date expires;
}
