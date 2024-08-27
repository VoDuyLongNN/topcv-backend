package com.vn.topcv.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/avatars/**")
		.addResourceLocations("file:E:/Study/FresherFsoft/TOPIC8_FINAL_PROJECT/backend/topcv/avatars/");
  }
}
