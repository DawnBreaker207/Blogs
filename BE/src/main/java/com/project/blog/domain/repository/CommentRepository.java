package com.project.blog.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.domain.model.Article;
import com.project.blog.domain.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findAllByArticle(Article article);

}
