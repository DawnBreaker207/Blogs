package com.project.blog.api.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("api.security")
public class AuthProperties {

    @NotNull
    private Token token;

    @Getter
    @Setter
    @Validated
    public static class Token {
	@NotBlank
	private String secret;

	@NotNull
	private long expiration;
    }
}
