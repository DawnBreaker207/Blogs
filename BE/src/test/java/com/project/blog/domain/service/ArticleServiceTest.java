package com.project.blog.domain.service;

import org.mockito.Mock;

import com.github.slugify.Slugify;
import com.project.blog.domain.repository.ArticleRepository;
import com.project.blog.domain.repository.CommentRepository;
import com.project.blog.infra.spec.ArticleSpecification;

public class ArticleServiceTest {
    
    
    @Mock
    private Slugify slg;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleSpecification spec;
    @Mock
    private CommentRepository commentRepository;
}
