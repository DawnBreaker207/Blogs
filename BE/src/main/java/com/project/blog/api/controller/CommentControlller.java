package com.project.blog.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.api.assembler.CommentAssembler;
import com.project.blog.api.model.comment.CommentRegister;
import com.project.blog.api.model.comment.CommentResponse;
import com.project.blog.api.security.AuthUtils;
import com.project.blog.api.security.authorization.CheckSecurity;
import com.project.blog.domain.service.ArticleService;
import com.project.blog.domain.service.CommentService;
import com.project.blog.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles/{slug}/comments")
public class CommentControlller {

    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final CommentAssembler commentAssembler;
    private final AuthUtils authUtils;

    @GetMapping
    @CheckSecurity.Public.canRead
    public CommentWrapper listByArticle(@PathVariable String slug) {
	var article = articleService.getBySlug(slug);

	if (authUtils.isAuthenticated()) {
	    var profile = userService.getCurrentUser().getProfile();
	    return commentAssembler.toCollectionReponse(profile, commentService.getAllByArticle(article));
	}

	return commentAssembler.toCollectionReponse(commentService.getAllByArticle(article));
    }

    @PostMapping
    @CheckSecurity.Proteced.canManage
    @ResponseStatus(code = HttpStatus.CREATED)
    public CommentResponse save(@PathVariable String slug, @Valid @RequestBody CommentRegister register) {
	var comment = commentAssembler.toEntity(register);
	var article = articleService.getBySlug(slug);
	var author = userService.getCurrentUser().getProfile();

	return commentAssembler.toResponse(commentService.save(comment, article, author));
    }

    @DeleteMapping("/{commentId}")
    @CheckSecurity.Comments.canDelete
    public void delete(@PathVariable String slug, @PathVariable Long commentId) {
	var article = articleService.getBySlug(slug);
	var comment = commentService.getById(commentId);

	commentService.delete(comment);
    }
}
