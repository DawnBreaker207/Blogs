package com.project.blog.api.security.authorization;

import org.springframework.stereotype.Component;

import com.project.blog.api.security.AuthUtils;
import com.project.blog.domain.model.Profile;
import com.project.blog.domain.service.ArticleService;
import com.project.blog.domain.service.CommentService;
import com.project.blog.domain.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorizationConfig {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;

    public boolean isArticleAuthor(String slug) {
	if (!isAuthenticated()) {
	    return false;
	}

	var article = articleService.getBySlug(slug);
	var author = article.getAuthor();

	return authenticatedUserEquals(author);
    }

    public boolean isCommentAuthor(Long commentId) {
	if (!isAuthenticated()) {
	    return false;
	}

	var comment = commentService.getById(commentId);
	var author = comment.getAuthor();

	return authenticatedUserEquals(author);
    }

    private boolean authenticatedUserEquals(Profile user) {
	return userService.getCurrentUser().getProfile().equals(user);
    }

    public boolean isAuthenticated() {
	return authUtils.isAuthenticated();
    }
}
