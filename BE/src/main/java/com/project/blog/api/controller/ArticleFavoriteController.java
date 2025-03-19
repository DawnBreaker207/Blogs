package com.project.blog.api.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.api.assembler.ArticleAssembler;
import com.project.blog.api.model.article.ArticleResponse;
import com.project.blog.api.security.authorization.CheckSecurity;
import com.project.blog.domain.service.ArticleService;
import com.project.blog.domain.service.ProfileService;
import com.project.blog.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}")
public class ArticleFavoriteController {

    private final UserService userService;
    private final ProfileService profileService;
    private final ArticleService articleService;
    private final ArticleAssembler articleAssembler;

    @PostMapping("/favorite")
    @CheckSecurity.Proteced.canManage
    public ArticleResponse favoriteArticle(@PathVariable String slug) {
	var article = articleService.getBySlug(slug);
	var profile = userService.getCurrentUser().getProfile();

	profile = profileService.favorite(profile, article);
	article = articleService.profileFavorited(profile, article);
	return articleAssembler.toResponse(profile, article);
    }

    @DeleteMapping("/favorite")
    @CheckSecurity.Proteced.canManage
    public ArticleResponse unfavoriteArticle(@PathVariable String slug) {
	var article = articleService.getBySlug(slug);
	var profile = userService.getCurrentUser().getProfile();

	profile = profileService.unfavorite(profile, article);
	article = articleService.profileUnfavorited(profile, article);

	return articleAssembler.toResponse(profile, article);
    }
}
