package com.vn.topcv.config;

import com.vn.topcv.jwt.JwtFilterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Autowired
  private JwtFilterConfig jwtFilterConfig;

  @Autowired
  private AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	httpSecurity
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(
			(auth) -> {
			  auth.requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN", "SYSTEM");
			  auth.requestMatchers("/api/v1/auth/**").permitAll();
			  auth.requestMatchers("/avatars/**", "/public/**", "/province/get-all",
				  "/job-category/get-all", "/chat-websocket/**", "/app/**").permitAll();
			  auth.anyRequest().authenticated();
			})
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtFilterConfig, UsernamePasswordAuthenticationFilter.class);

	return httpSecurity.build();
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
	return new WebMvcConfigurer() {
	  @Override
	  public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:5173")
			.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
			.allowCredentials(true);
	  }
	};
  }
}
