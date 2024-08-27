package com.vn.topcv.dto.request;

import com.vn.topcv.entity.enums.ESenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatSendMessageRequest {

  private String conversationId;
  private String senderId;
  private String content;
  private String senderType;
}
