package com.project.blog.domain.exception;

public class ArticleNotUniqueException extends BusinessException {

    public ArticleNotUniqueException() {
	super("There's already an article with this title");
    }
}
