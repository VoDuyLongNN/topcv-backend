package com.vn.topcv.config;

import com.vn.topcv.jwt.JwtChannelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Autowired
  private JwtChannelInterceptor jwtChannelInterceptor;

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
	config.enableSimpleBroker("/topic");
	config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
	registry.addEndpoint("/chat-websocket")
		.setAllowedOrigins("http://localhost:5173", "http://127.0.0.1:5500")
		.withSockJS();
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
	registration.interceptors(jwtChannelInterceptor);
  }
}
