package com.project.blog.api.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse httpResponse,
	    AccessDeniedException accessDeniedException) throws IOException, ServletException {
	var status = HttpStatus.FORBIDDEN.value();
	var error = Error.builder().status("error").build();
	
	
	
	
	httpResponse.setStatus(status);
	httpResponse.setContentType("application/json");
	
	var out = httpResponse.getOutputStream();
	var mapper = new ObjectMapper();
	mapper.writerWithDefaultPrettyPrinter().writeValue(out, error);
	out.flush();
    }
}
