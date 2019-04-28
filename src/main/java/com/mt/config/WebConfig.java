package com.mt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mt.interceptor.AJaxInterceptor;
import com.mt.interceptor.UserInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	@Autowired
	private AJaxInterceptor aJaxInterceptor;
	
	@Autowired
	private UserInterceptor userInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(aJaxInterceptor).addPathPatterns("/**");
		registry.addInterceptor(userInterceptor).addPathPatterns("/boss/**");
	}
	
}
