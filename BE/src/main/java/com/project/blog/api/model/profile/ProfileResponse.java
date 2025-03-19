package com.project.blog.api.model.profile;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.blog.api.model.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("profile")
public class ProfileResponse extends BaseResponse {

    private String username;
    private String bio;
    private String image;
    private boolean following;
}
