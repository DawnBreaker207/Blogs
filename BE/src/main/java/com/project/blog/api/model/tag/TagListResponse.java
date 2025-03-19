package com.project.blog.api.model.tag;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TagListResponse {
    private List<String> tags;
}
