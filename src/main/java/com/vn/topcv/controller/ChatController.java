package com.vn.topcv.controller;

import com.vn.topcv.dto.request.ChatSendMessageRequest;
import com.vn.topcv.dto.request.ConversationCreateRequest;
import com.vn.topcv.service.IChatService;
import com.vn.topcv.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@PreAuthorize("hasAnyAuthority('ROLE_COMPANY', 'ROLE_PERSONAL')")
public class ChatController {

  @Autowired
  private IChatService chatService;

  @PostMapping("/create-conversation")
  public ResponseEntity<ResponseObject> createConversation(
	  @RequestBody ConversationCreateRequest request) {
	return chatService.createConversation(request);
  }

  @PostMapping("/send-message")
  public ResponseEntity<ResponseObject> sendMessage(@RequestBody ChatSendMessageRequest request) {
	return chatService.sendMessage(request);
  }

  @GetMapping("/get-messages/{id}")
  public ResponseEntity<ResponseObject> getMessages(@PathVariable("id") String id) {
	return chatService.getMessages(id);
  }
}
