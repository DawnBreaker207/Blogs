package com.project.blog.infra.spec;

import org.springframework.data.jpa.domain.Specification;
import com.project.blog.domain.model.Article;

import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.*;

@Join(path = "author", alias = "author")
@Join(path = "tagList", alias = "tags")
@Join(path = "favorites", alias = "fav")
@And({ @Spec(path = "author.username", params = "author", spec = Like.class),
	@Spec(path = "tags.name", params = "tag", spec = In.class),
	@Spec(path = "fav.username", params = "favorited", spec = In.class)

})
public interface ArticleSpecification extends Specification<Article> {

}
