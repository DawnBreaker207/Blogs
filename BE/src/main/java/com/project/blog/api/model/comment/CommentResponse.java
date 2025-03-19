package com.project.blog.api.model.comment;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.blog.api.model.BaseResponse;
import com.project.blog.api.model.profile.ProfileResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("comment")
public class CommentResponse extends BaseResponse {

    private Long id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private String body;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private ProfileResponse author;
}
