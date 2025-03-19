package com.project.blog.infra.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.blog.api.model.user.UserUpdate;
import com.project.blog.domain.model.User;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
	var modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

	var updateUserTypeMap = modelMapper.createTypeMap(UserUpdate.class, User.class);
	updateUserTypeMap.<String>addMapping(UserUpdate::getBio,
		(destination, value) -> destination.getProfile().setBio(value));
	updateUserTypeMap.<String>addMapping(UserUpdate::getImage,
		(destination, value) -> destination.getProfile().setImage(value));
	updateUserTypeMap.<String>addMapping(UserUpdate::getUsername,
		(destination, value) -> destination.getProfile().setUsername(value));

	return modelMapper;

    }
}
