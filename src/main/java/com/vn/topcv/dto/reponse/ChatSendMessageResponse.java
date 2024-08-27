package com.vn.topcv.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatSendMessageResponse {

  private String conversationId;
  private String senderId;
  private String content;
  private String senderType;

}
