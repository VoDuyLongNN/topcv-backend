package com.vn.topcv.jwt;

import com.vn.topcv.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilterConfig extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;

  @Autowired
  private JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
	  FilterChain filterChain) throws ServletException, IOException {

	final String authorizationHeader = request.getHeader("Authorization");
	final String email;
	final String jwt;

	if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
	  filterChain.doFilter(request, response);
	  return;
	}

	jwt = authorizationHeader.substring(7);

	try{
	  email = jwtService.extractEmailFromToken(jwt);

	  if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

		if (jwtService.isTokenValid(jwt, userDetails)) {
		  UsernamePasswordAuthenticationToken authenticationToken =
			  new UsernamePasswordAuthenticationToken(
				  userDetails, null, userDetails.getAuthorities());

		  authenticationToken.setDetails(
			  new WebAuthenticationDetailsSource().buildDetails(request));

		  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		} else {
		  throw new CustomException("JWT token is invalid or expired");
		}
	  }
	} catch (CustomException e) {
	  response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired JWT token");
	  return;
	}

	filterChain.doFilter(request, response);
  }
}
