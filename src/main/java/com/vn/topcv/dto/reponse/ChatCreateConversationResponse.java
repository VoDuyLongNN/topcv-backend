package com.vn.topcv.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatCreateConversationResponse {

  private String id;
  private String companyId;
  private String personalId;
  private String postId;
  private String createDate;
}
