package com.project.blog.domain.exception;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException() {
	super("Not found");
    }
}
