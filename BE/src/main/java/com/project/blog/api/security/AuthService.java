package com.project.blog.api.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.project.blog.api.assembler.UserAssembler;
import com.project.blog.api.model.user.UserAuthenticate;
import com.project.blog.api.model.user.UserResponse;
import com.project.blog.api.model.user.UserToken;
import com.project.blog.domain.model.User;
import com.project.blog.domain.service.ProfileService;
import com.project.blog.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final UserAssembler userAssembler;
    private final AuthenticationManager authenticationManager;

    public UserResponse regiser(User user) {
	var token = tokenService.generateToken(setDefaultClaims(user), user.getEmail());
	userService.setToken(user, token);
	return toUserResponse(user);
    }

    public UserResponse authenticate(UserAuthenticate authenticate) {
	authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(authenticate.getEmail(), authenticate.getPassword()));

	var user = userService.getByEmail(authenticate.getEmail());
	var token = tokenService.generateToken(setDefaultClaims(user), user.getEmail());

	userService.setToken(user, token);
	return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {
	return userAssembler.toResponse(user);
    }

    private Map<String, Object> setDefaultClaims(User user) {
	var claims = new HashMap<String, Object>();
	var userToken = UserToken.builder().id(user.getId()).build();

	claims.put("user", userToken);
	return claims;
    }
}
