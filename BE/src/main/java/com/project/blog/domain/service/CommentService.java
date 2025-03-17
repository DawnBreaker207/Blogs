package com.project.blog.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.blog.domain.exception.CommentNotFoundException;
import com.project.blog.domain.model.Article;
import com.project.blog.domain.model.Comment;
import com.project.blog.domain.model.Profile;
import com.project.blog.domain.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository repository;

    public List<Comment> getAllByArticle(Article article) {
	return repository.findAllByArticle(article);
    }

    @Transactional
    public Comment getById(Long commentId) {
	return repository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    @Transactional
    public Comment save(Comment comment, Article article, Profile author) {
	comment.setAuthor(author);
	comment.setArticle(article);
	return repository.save(comment);
    }

    @Transactional
    public void delete(Comment comment) {
	repository.delete(comment);
    }
}
