package com.project.blog.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.api.assembler.TagAssembler;
import com.project.blog.api.model.tag.TagListResponse;
import com.project.blog.api.security.authorization.CheckSecurity;
import com.project.blog.domain.service.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagControlller {

    private final TagService tagService;
    private final TagAssembler tagAssembler;

    @GetMapping
    @CheckSecurity.Public.canRead
    public TagListResponse list() {
	return tagAssembler.toCollectionResponse(tagService.listAll());
    }
}
