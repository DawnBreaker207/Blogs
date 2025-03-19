package com.project.blog.api.model.comment;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentWrapper {

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    private List<CommentResponse> comments;
}
