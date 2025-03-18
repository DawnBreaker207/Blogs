package com.project.blog.api.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse httpResponse,
	    AuthenticationException authException) throws IOException, ServletException {
	var status = HttpStatus.UNAUTHORIZED;
	var error = Error.builder().message("Missing credentials or token invalid").status("error").build();

	httpResponse.setStatus(status.value());
	httpResponse.setContentType("application/json");
	var out = httpResponse.getOutputStream();
	var mapper = new ObjectMapper();
	mapper.writerWithDefaultPrettyPrinter().writeValue(out, error);
	out.flush();
    }
}
