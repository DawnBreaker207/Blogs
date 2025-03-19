package com.project.blog.api.model.user;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.blog.api.model.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("user")
public class UserResponse extends BaseResponse {

    private String email;

    private String username;
    private String bio;
    private String image;

    private String token;

}
