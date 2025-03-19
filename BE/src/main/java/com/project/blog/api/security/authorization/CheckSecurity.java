package com.project.blog.api.security.authorization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {

    public @interface Public {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("permitAll()")
	public @interface canRead {
	}
    }
    
    public @interface Proteced {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@authorizationConfig.isAuthenticated")
	public @interface canManage {
	}
    }

    public @interface Articles {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@authorizationConfig.isAritcleAuthor(#slug)")
	public @interface canManage {
	}
    }

    public @interface Comments {
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@PreAuthorize("@authorizationConfig.isCommentAuthor(#commentId)")
	public @interface canDelete {
	}
    }
}
