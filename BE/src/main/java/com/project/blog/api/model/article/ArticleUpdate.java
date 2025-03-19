package com.project.blog.api.model.article;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.blog.api.model.BaseResponse;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("article")
public class ArticleUpdate extends BaseResponse {

    @Nullable
    private String title;

    @Nullable
    private String description;

    @Nullable
    private String body;
}
