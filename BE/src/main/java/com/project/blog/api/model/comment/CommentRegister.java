package com.project.blog.api.model.comment;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.blog.api.model.BaseResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("comment")
public class CommentRegister extends BaseResponse {

    @NotBlank
    private String body;
}
