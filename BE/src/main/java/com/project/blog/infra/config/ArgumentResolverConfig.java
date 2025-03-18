package com.project.blog.infra.config;

import java.util.List;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;

@SpringBootConfiguration
@EnableAutoConfiguration
public class ArgumentResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvres) {
	resolvres.add(new SpecificationArgumentResolver());
	resolvres.add(new PageableHandlerMethodArgumentResolver());
    }
}
