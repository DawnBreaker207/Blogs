package com.project.blog.api.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.api.assembler.UserAssembler;
import com.project.blog.api.model.user.UserAuthenticate;
import com.project.blog.api.model.user.UserRegister;
import com.project.blog.api.model.user.UserResponse;
import com.project.blog.domain.service.ProfileService;
import com.project.blog.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final UserAssembler userAssembler;
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<UserResponse> regiser(@Valid @RequestBody UserRegister register) {
	var user = userAssembler.toEntity(register);
	var profile = profileService.createNewProfile(user, register.getUsername());

	return ResponseEntity.ok(authService.regiser(userService.save(user, profile)));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@Valid @RequestBody UserAuthenticate authenticate) {
	return ResponseEntity.ok(authService.authenticate(authenticate));
    }

}
