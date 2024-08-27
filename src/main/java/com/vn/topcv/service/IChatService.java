package com.vn.topcv.service;

import com.vn.topcv.dto.request.ChatSendMessageRequest;
import com.vn.topcv.dto.request.CompanyUpdateRequest;
import com.vn.topcv.dto.request.ConversationCreateRequest;
import com.vn.topcv.entity.Conversation;
import com.vn.topcv.entity.Message;
import com.vn.topcv.entity.enums.ESenderType;
import com.vn.topcv.util.ResponseObject;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface IChatService {

  ResponseEntity<ResponseObject> createConversation(ConversationCreateRequest request);

  ResponseEntity<ResponseObject> sendMessage(ChatSendMessageRequest request);

  ResponseEntity<ResponseObject> getMessages(String conversationId);

}
