package com.project.blog.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

    private Authentication getAuthentication() {
	return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentEmail() {
	return getAuthentication().getName();
    }

    public boolean isAuthenticated() {
	return getAuthentication() != null;
    }
}
