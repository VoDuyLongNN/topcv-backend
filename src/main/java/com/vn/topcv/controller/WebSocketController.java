package com.vn.topcv.controller;

import com.vn.topcv.dto.response.ChatSendMessageResponse;
import com.vn.topcv.dto.request.ChatSendMessageRequest;
import com.vn.topcv.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

  @Autowired
  private IChatService chatService;

  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public ChatSendMessageResponse sendMessage(@Payload ChatSendMessageRequest request) {

    return (ChatSendMessageResponse) chatService.sendMessage(request).getBody().getData();
  }
}
