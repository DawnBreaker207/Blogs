package com.project.blog.api.model.article;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.blog.api.model.BaseResponse;
import com.project.blog.api.model.profile.ProfileResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("article")
public class ArticleReponse extends BaseResponse {

    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean favorited;
    private int favoritedCount;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
    private ProfileResponse author;
}
