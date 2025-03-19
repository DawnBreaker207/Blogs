package com.project.blog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.api.assembler.UserAssembler;
import com.project.blog.api.model.user.UserResponse;
import com.project.blog.api.model.user.UserUpdate;
import com.project.blog.api.security.authorization.CheckSecurity;
import com.project.blog.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    @GetMapping
    @CheckSecurity.Proteced.canManage
    public UserResponse getCurrentUser() {
	var user = userService.getCurrentUser();
	return userAssembler.toResponse(user);
    }

    @PutMapping
    @CheckSecurity.Proteced.canManage
    public UserResponse updateCurrentUser(@RequestBody UserUpdate userUpdate) {
	var currentUser = userService.getCurrentUser();
	userAssembler.copyToEntity(userUpdate, currentUser);
	return userAssembler.toResponse(userService.save(currentUser, currentUser.getProfile()));
    }
}
