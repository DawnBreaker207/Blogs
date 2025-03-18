package com.project.blog.api.exception;

import java.util.*;

import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.blog.domain.exception.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "Oops! Something went wrong.";

    private Error.ErrorBuilder createErrorBuilder(Map<String, Object> errors) {
	return Error.builder().errors(errors);
    }

    private Error.ErrorBuilder createErrorBuilder(String message) {
	return Error.builder().status("error").message(message);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
	    HttpStatusCode statusCode, WebRequest request) {
	if (body == null || body instanceof String) {
	    body = createErrorBuilder(GENERIC_ERROR_MESSAGE).build();

	}
	return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request, String name) {
	var status = HttpStatus.NOT_FOUND;
	var error = createErrorBuilder(toMap(name, ex.getMessage())).build();

	return handleExceptionInternal(ex, name, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(exception = ArticleNotFoundException.class)
    public ResponseEntity<?> handleArticleNotFound(ArticleNotFoundException ex, WebRequest request) {
	return handleResourceNotFound(ex, request, "article");
    }

    @ExceptionHandler(exception = EmailNotFoundException.class)
    public ResponseEntity<?> handleEmailNotFound(EmailNotFoundException ex, WebRequest request) {
	return handleResourceNotFound(ex, request, "email");
    }

    @ExceptionHandler(exception = TagNotFoundException.class)
    public ResponseEntity<?> handleTagNotFound(TagNotFoundException ex, WebRequest request) {
	return handleResourceNotFound(ex, request, "tag");
    }

    @ExceptionHandler(exception = ProfileNotFoundException.class)
    public ResponseEntity<?> handleProfileNotFound(ProfileNotFoundException ex, WebRequest request) {
	return handleResourceNotFound(ex, request, "profile");
    }

    @ExceptionHandler(exception = CommentNotFoundException.class)
    public ResponseEntity<?> handleCommentNotFound(CommentNotFoundException ex, WebRequest request) {
	return handleResourceNotFound(ex, request, "comment");
    }

    @ExceptionHandler(exception = BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex, WebRequest request) {
	var status = HttpStatus.INTERNAL_SERVER_ERROR;

	var error = createErrorBuilder(ex.getMessage()).build();

	return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    private ResponseEntity<?> handleTaken(TakenException ex, WebRequest request, String fieldName) {
	var status = HttpStatus.UNPROCESSABLE_ENTITY;
	var message = ex.getMessage();

	var error = createErrorBuilder(toMap(fieldName, message)).build();
	return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(exception = EmailTakenException.class)
    public ResponseEntity<?> handleEmailTaken(EmailTakenException ex, WebRequest request) {
	return handleTaken(ex, request, "email");
    }

    @ExceptionHandler(exception = UsernameTakenException.class)
    public ResponseEntity<?> handleAccessDenied(UsernameTakenException ex, WebRequest request) {
	return handleTaken(ex, request, "username");
    }

    @ExceptionHandler(exception = AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
	var status = HttpStatus.FORBIDDEN;
	var message = "You don't have access to this resource";

	var error = createErrorBuilder(message).build();

	return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(exception = ArticleNotUniqueException.class)
    public ResponseEntity<?> handleArticleNotUnique(ArticleNotUniqueException ex, WebRequest request) {
	var status = HttpStatus.UNPROCESSABLE_ENTITY;
	var message = ex.getMessage();

	var error = createErrorBuilder(toMap("title", message)).build();

	return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(exception = IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
	var status = HttpStatus.UNPROCESSABLE_ENTITY;
	var message = ex.getMessage();

	var error = createErrorBuilder(message).build();

	return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	    HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
	    HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
	var status = HttpStatus.valueOf(statusCode.value());

	var map = new HashMap<String, Object>();
	bindingResult.getFieldErrors().forEach(fieldError -> {
	    map.put(fieldError.getField(), toList(fieldError.getDefaultMessage()));
	});

	var error = createErrorBuilder(map).build();
	return handleExceptionInternal(ex, error, headers, status, request);
    }

    private List<String> toList(String message) {
	var messageList = new ArrayList<String>();
	messageList.add(message);
	return messageList;
    }

    private Map<String, Object> toMap(String field, String message) {
	var map = new HashMap<String, Object>();
	map.put(field, toList(message));
	return map;
    }
}
