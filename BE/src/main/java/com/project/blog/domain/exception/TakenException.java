package com.project.blog.domain.exception;

public class TakenException extends BusinessException {

    public TakenException() {
	super("Has already been taken");
    }
}
