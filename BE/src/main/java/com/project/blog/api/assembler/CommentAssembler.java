package com.project.blog.api.assembler;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project.blog.api.model.comment.CommentRegister;
import com.project.blog.api.model.comment.CommentResponse;
import com.project.blog.api.model.comment.CommentWrapper;
import com.project.blog.domain.model.Comment;
import com.project.blog.domain.model.Profile;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentAssembler {

    private final ModelMapper modelMapper;

    public CommentResponse toResponse(Comment comment) {
	return modelMapper.map(comment, CommentResponse.class);
    }

    public CommentResponse toResponse(Profile profile, Comment comment) {
	var response = toResponse(comment);

	if (profile.getProfiles().contains(comment.getAuthor())) {
	    response.getAuthor().setFollowing(true);
	}
	return response;
    }

    public CommentWrapper toCollectionReponse(List<Comment> comments) {
	var content = comments.stream().map(this::toResponse).toList();

	return buildResponse(content);
    }

    public CommentWrapper toCollectionResponse(Profile profile, List<Comment> comments) {
	var content = comments.stream().map(c -> toResponse(profile, c)).toList();
	return buildResponse(content);
    }

    private CommentWrapper buildResponse(List<CommentResponse> comments) {
	return CommentWrapper.builder().comments(comments).build();
    }

    public Comment toEntity(CommentRegister register) {
	return modelMapper.map(register, Comment.class);
    }
}
