package com.project.blog.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.blog.api.exception.RestAccessDeniedHandler;
import com.project.blog.api.exception.RestAuthenticationEntryPoint;

@Configuration
public class CustomHandlersConfig {
    @Bean
    public RestAccessDeniedHandler restAccessDeniedHandler() {
	return new RestAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
	return new RestAuthenticationEntryPoint();
    }
}
