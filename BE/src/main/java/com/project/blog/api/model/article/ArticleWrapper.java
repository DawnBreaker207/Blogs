package com.project.blog.api.model.article;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArticleWrapper {

    @JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
    private List<ArticleReponse> articles;
    private int articlesCount;
}
