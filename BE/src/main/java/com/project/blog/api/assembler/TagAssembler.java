package com.project.blog.api.assembler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.project.blog.api.model.tag.TagListResponse;
import com.project.blog.domain.model.Tag;

@Component
public class TagAssembler {
    public TagListResponse toCollectionResponse(List<Tag> tags) {
	var tagsNames = tags.stream().map(Tag::getName).toList();
	return TagListResponse.builder().tags(tagsNames).build();
    }
}
