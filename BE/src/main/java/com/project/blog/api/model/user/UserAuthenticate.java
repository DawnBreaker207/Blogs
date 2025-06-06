package com.project.blog.api.model.user;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.blog.api.model.BaseResponse;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("user")
public class UserAuthenticate extends BaseResponse {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
