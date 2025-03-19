package com.project.blog.api.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.project.blog.api.assembler.ProfileAssembler;
import com.project.blog.api.model.profile.ProfileResponse;
import com.project.blog.api.security.AuthUtils;
import com.project.blog.api.security.authorization.CheckSecurity;
import com.project.blog.domain.service.ProfileService;
import com.project.blog.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileAssembler profileAssembler;
    private final AuthUtils authUtils;

    @GetMapping("/{username}")
    @CheckSecurity.Public.canRead
    public ProfileResponse getByUsername(@PathVariable String username, WebRequest request) {
	if (authUtils.isAuthenticated()) {
	    var currentUser = userService.getCurrentUser().getProfile();
	    return profileAssembler.toResponse(currentUser, profileService.getByUsername(username));
	}

	return profileAssembler.toResponse(profileService.getByUsername(username));
    }

    @PostMapping("/{username}/follow")
    @CheckSecurity.Proteced.canManage
    public ProfileResponse followProfile(@PathVariable String username) {
	var toFollow = profileService.getByUsername(username);
	var current = userService.getCurrentUser().getProfile();

	profileService.follow(current, toFollow);
	return profileAssembler.toResponse(current, toFollow);
    }

    @DeleteMapping("/{username}/follow")
    @CheckSecurity.Proteced.canManage
    public ProfileResponse unfollowProfile(@PathVariable String username) {
	var toFollow = profileService.getByUsername(username);
	var current = userService.getCurrentUser().getProfile();

	profileService.unfollow(current, toFollow);
	return profileAssembler.toResponse(current, toFollow);
    }

}
