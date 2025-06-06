package com.project.blog.api.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse reponse, FilterChain filterChain)
	    throws ServletException, IOException {
	final String token;
	final String email;
	final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

	if (authHeader == null || !authHeader.startsWith("Token ")) {
	    filterChain.doFilter(request, reponse);
	    return;
	}

	token = authHeader.substring(6);
	email = tokenService.extractEmail(token);

	if (email != null && !isAuthenticated()) {
	    var userDetails = userDetailsService.loadUserByUsername(email);

	    if (tokenService.isTokenValid(token, userDetails.getUsername())) {
		var authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
			userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	    }
	}

	filterChain.doFilter(request, reponse);

    }

    private boolean isAuthenticated() {
	return SecurityContextHolder.getContext().getAuthentication() != null;
    }
}
