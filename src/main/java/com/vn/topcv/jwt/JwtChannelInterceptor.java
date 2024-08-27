package com.vn.topcv.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

  @Autowired
  private final JwtService jwtService;

  @Autowired
  private final UserDetailsService userDetailsService;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
	StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
		StompHeaderAccessor.class);

	if (accessor != null) {
	  String jwt = accessor.getFirstNativeHeader("Authorization");

	  if (jwt != null && jwt.startsWith("Bearer ")) {
		jwt = jwt.substring(7);
		String email = jwtService.extractEmailFromToken(jwt);

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		  UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		  if (jwtService.isTokenValid(jwt, userDetails)) {
			UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		  }
		}
	  }
	}

	return message;
  }
}
