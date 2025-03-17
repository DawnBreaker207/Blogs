package com.project.blog.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.project.blog.domain.model.Article;
import com.project.blog.domain.model.Profile;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    
    Optional<Article> findBySlug(String slug);
    List<Article> findAllByAuthorIn(List<Profile> profiles, Pageable pageable);

}
