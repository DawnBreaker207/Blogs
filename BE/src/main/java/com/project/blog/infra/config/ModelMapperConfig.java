package com.project.blog.infra.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
	var modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
   
	var updateUserTypeMap = modelMapper.createTypeMap(UserUPdate, null)
    }
}
