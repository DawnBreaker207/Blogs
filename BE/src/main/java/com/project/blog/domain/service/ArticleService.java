package com.project.blog.domain.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.slugify.Slugify;
import com.project.blog.domain.exception.ArticleNotFoundException;
import com.project.blog.domain.exception.ArticleNotUniqueException;
import com.project.blog.domain.model.Article;
import com.project.blog.domain.model.Profile;
import com.project.blog.domain.model.Tag;
import com.project.blog.domain.repository.ArticleRepository;
import com.project.blog.infra.spec.ArticleSpecification;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final Slugify slg;
    private final ArticleRepository repository;

    @Transactional(readOnly = true)
    public Page<Article> listAll(ArticleSpecification filter, Pageable pageable) {
	return repository.findAll(filter, pageable);
    }

    @Transactional(readOnly = true)
    public Article getBySlug(String slug) {
	return repository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Article> getFeedByUser(Profile profile, Pageable pageable) {
	List<Profile> followedUsers = profile.getProfiles().stream().toList();

	return repository.findAllByAuthorIn(followedUsers, pageable);
    }

    @Transactional
    public Article save(Article article, Profile profile, List<Tag> tags) {
	addAllTags(article, tags);
	article.setAuthor(profile);
	return save(article);
    }

    @Transactional
    public Article save(Article article) {
	var slug = slg.slugify(article.getTitle());
	checkSlugAvailability(slug, article);
	article.setSlug(slug);

	return repository.save(article);
    }

    @Transactional
    public void delete(Article article) {
	var favorited = article.getFavorites();
	favorited.forEach(u -> u.unfavoriteArticle(article));

	repository.delete(article);
    }

    @Transactional
    public Article profileFavorited(Profile profile, Article article) {
	article.addFavorite(profile);
	return repository.save(article);
    }

    @Transactional
    public Article profileUnfavorited(Profile profile, Article article) {
	article.removeFavorite(profile);
	return repository.save(article);
    }

    private void addAllTags(Article article, List<Tag> tags) {
	article.setTagList(new HashSet<>());
	tags.forEach(article::addTag);
    }

    private boolean slugTaken(String slug, Article article) {
	var existingArticle = repository.findBySlug(slug);
	return existingArticle.isPresent() && !existingArticle.get().equals(article);
    }

    private void checkSlugAvailability(String slug, Article article) {
	if (slugTaken(slug, article))
	    throw new ArticleNotUniqueException();
    }

}
