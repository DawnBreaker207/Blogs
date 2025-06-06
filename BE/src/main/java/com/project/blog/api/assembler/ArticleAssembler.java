package com.project.blog.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project.blog.api.model.article.ArticleRegister;
import com.project.blog.api.model.article.ArticleResponse;
import com.project.blog.api.model.article.ArticleUpdate;
import com.project.blog.api.model.article.ArticleWrapper;
import com.project.blog.domain.model.Article;
import com.project.blog.domain.model.Profile;
import com.project.blog.domain.model.Tag;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArticleAssembler {

    private final ModelMapper modelMapper;

    public void copyToEntity(ArticleUpdate update, Article article) {
	modelMapper.map(update, article);
    }

    public ArticleResponse toResponse(Article article) {
	var response = modelMapper.map(article, ArticleResponse.class);

	response.setTagList(tagsToList(article.getTagList().stream().toList()));

	return response;
    }

    public ArticleResponse toResponse(Profile profile, Article article) {
	var response = toResponse(article);

	var isFollowingProfile = profile.getProfiles().contains(article.getAuthor());
	response.getAuthor().setFollowing(isFollowingProfile);

	var isArticleFavorited = article.getFavorites().contains(profile);
	response.setFavorited(isArticleFavorited);

	return response;
    }

    public ArticleWrapper toCollectionModel(List<Article> articles) {

	var content = articles.stream().map(this::toResponse).toList();

	return buildResponse(content);
    }

    public ArticleWrapper toCollectionModel(Profile profile, List<Article> articles) {
	var content = articles.stream().map(a -> toResponse(profile, a)).toList();

	return buildResponse(content);
    }

    private ArticleWrapper buildResponse(List<ArticleResponse> articles) {
	return ArticleWrapper.builder().articles(articles).articlesCount(articles.size()).build();
    }

    public Article toEntity(ArticleRegister register) {
	return modelMapper.map(register, Article.class);
    }

    private List<String> tagsToList(List<Tag> tags) {
	return tags.stream().map(Tag::getName).toList();
    }
}
