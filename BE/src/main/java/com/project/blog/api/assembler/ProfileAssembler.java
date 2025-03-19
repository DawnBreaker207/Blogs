package com.project.blog.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project.blog.api.model.profile.ProfileResponse;
import com.project.blog.domain.model.Profile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileAssembler {
    private final ModelMapper modelMapper;

    public ProfileResponse toResponse(Profile profile) {
	return modelMapper.map(profile, ProfileResponse.class);
    }

    public ProfileResponse toResponse(Profile current, Profile profile) {
	var response = toResponse(profile);

	var isFollowing = current.getProfiles().contains(profile);
	response.setFollowing(isFollowing);

	return response;
    }
}
