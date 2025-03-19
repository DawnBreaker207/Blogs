package com.project.blog.api.model.article;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.project.blog.api.model.BaseResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonTypeName("article")
public class ArticleRegister extends BaseResponse {

    private String title;
    private String description;
    private String body;
    private Set<String> tagList;

}
