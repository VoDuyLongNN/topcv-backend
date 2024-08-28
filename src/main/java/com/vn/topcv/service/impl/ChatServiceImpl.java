package com.vn.topcv.service.impl;

import com.vn.topcv.dto.response.ChatCreateConversationResponse;
import com.vn.topcv.dto.response.ChatSendMessageResponse;
import com.vn.topcv.dto.request.ChatSendMessageRequest;
import com.vn.topcv.dto.request.ConversationCreateRequest;
import com.vn.topcv.entity.Company;
import com.vn.topcv.entity.Conversation;
import com.vn.topcv.entity.Message;
import com.vn.topcv.entity.Personal;
import com.vn.topcv.entity.PostPersonal;
import com.vn.topcv.entity.User;
import com.vn.topcv.entity.enums.ESenderType;
import com.vn.topcv.exception.CustomException;
import com.vn.topcv.repository.ICompanyRepository;
import com.vn.topcv.repository.IConversationRepository;
import com.vn.topcv.repository.IMessageRepository;
import com.vn.topcv.repository.IPersonalRepository;
import com.vn.topcv.repository.IPostPersonalRepository;
import com.vn.topcv.service.IChatService;
import com.vn.topcv.util.ResponseObject;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements IChatService {

  @Autowired
  private IConversationRepository conversationRepository;

  @Autowired
  private IMessageRepository messageRepository;

  @Autowired
  private ICompanyRepository companyRepository;

  @Autowired
  private IPersonalRepository personalRepository;

  @Autowired
  private IPostPersonalRepository postPersonalRepository;

  @Override
  public ResponseEntity<ResponseObject> createConversation(ConversationCreateRequest request) {
	ResponseObject responseObject;
	HttpStatus status;

	try {
	  User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	  if(user == null) {
		throw new CustomException("User not logged in");
	  }

	  Long personalId = Long.parseLong(request.getPersonalId());
	  Long postId = Long.parseLong(request.getPostId());

	  Company company = companyRepository.findByUserId(user.getUserId())
		  .orElseThrow(() -> new CustomException("Company not found!"));
	  Personal personal = personalRepository.findById(personalId)
		  .orElseThrow(() -> new CustomException("Personal not found!"));
	  PostPersonal postPersonal = postPersonalRepository.findById(postId)
		  .orElseThrow(() -> new CustomException("PostPersonal not found!"));

	  Conversation conversation = conversationRepository.findByCompany_CompanyIdAndPersonalIdAndPostId(
		  company.getCompanyId(), personalId, postId);

	  if (conversation == null) {

		conversation = Conversation.builder()
			.company(company)
			.personal(personal)
			.post(postPersonal)
			.createDate(new Timestamp(System.currentTimeMillis()))
			.build();

		conversationRepository.save(conversation);
	  }

	  ChatCreateConversationResponse response = ChatCreateConversationResponse.builder()
		  .id(conversation.getId().toString())
		  .companyId(company.getCompanyId().toString())
		  .personalId(personalId.toString())
		  .postId(postId.toString())
		  .createDate(conversation.getCreateDate().toString())
		  .build();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Conversation Created!")
		  .data(response)
		  .build();

	  status = HttpStatus.OK;

	} catch (CustomException | NumberFormatException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.BAD_REQUEST;
	} catch (Exception e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<>(responseObject, status);
  }

  @Override
  public ResponseEntity<ResponseObject> sendMessage(ChatSendMessageRequest request) {
	ResponseObject responseObject;
	HttpStatus status;

	try {
	  Long conversationId = Long.parseLong(request.getConversationId());
	  Long senderId = Long.parseLong(request.getSenderId());
	  ESenderType eSenderType;

	  if (request.getSenderType().equals("COMPANY")) {
		eSenderType = ESenderType.COMPANY;
		companyRepository.findById(senderId)
			.orElseThrow(() -> new CustomException("Company not found!"));
	  } else if (request.getSenderType().equals("PERSONAL")) {
		eSenderType = ESenderType.PERSONAL;
		personalRepository.findById(conversationId)
			.orElseThrow(() -> new CustomException("Personal not found!"));
	  } else {
		throw new CustomException("Sender type not found!");
	  }

	  Conversation conversation = conversationRepository.findById(conversationId)
		  .orElseThrow(() -> new CustomException("Conversation not found!"));

	  Message message = Message.builder()
		  .conversation(conversation)
		  .senderId(senderId)
		  .content(request.getContent())
		  .senderType(eSenderType)
		  .createDate(new Timestamp(System.currentTimeMillis()))
		  .build();
	  messageRepository.save(message);

	  ChatSendMessageResponse response = ChatSendMessageResponse.builder()
		  .conversationId(conversationId.toString())
		  .senderId(senderId.toString())
		  .content(request.getContent())
		  .senderType(eSenderType.toString())
		  .build();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Message Sent!")
		  .data(response)
		  .build();

	  status = HttpStatus.OK;

	} catch (CustomException | NumberFormatException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.BAD_REQUEST;
	} catch (Exception e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	return new ResponseEntity<>(responseObject, status);
  }

  @Override
  public ResponseEntity<ResponseObject> getMessages(String conversationId) {
	ResponseObject responseObject;
	HttpStatus status;

	try {
	  Long id = Long.parseLong(conversationId);

	  List<Message> messages = messageRepository.findByConversationId(id);

	  List<ChatSendMessageResponse> responses = messages.stream()
		  .map(message -> new ChatSendMessageResponse(
			  String.valueOf(message.getConversation().getId()),
			  String.valueOf(message.getSenderId()),
			  message.getContent(),
			  message.getSenderType().name()
		  ))
		  .toList();

	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.OK.name())
		  .message("Get successfully!")
		  .data(responses)
		  .build();

	  status = HttpStatus.OK;

	} catch (NumberFormatException e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.BAD_REQUEST.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.BAD_REQUEST;

	} catch (Exception e) {
	  responseObject = ResponseObject.builder()
		  .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
		  .message(e.getMessage())
		  .build();

	  status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
	return new ResponseEntity<>(responseObject, status);
  }
}
