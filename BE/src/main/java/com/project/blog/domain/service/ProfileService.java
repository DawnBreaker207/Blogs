package com.project.blog.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.blog.domain.exception.ProfileNotFoundException;
import com.project.blog.domain.model.Article;
import com.project.blog.domain.model.Profile;
import com.project.blog.domain.model.User;
import com.project.blog.domain.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final String DEFAULT_IMAGE_URL = "https://api.realworld.io/images/smiley-cyrus.jpeg";

    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public Profile getByUsername(String username) {
	return profileRepository.findByUsername(username).orElseThrow(ProfileNotFoundException::new);
    }

    @Transactional
    public Profile save(Profile profile) {

	profile.setBio(profile.getBio());
	profile.setUsername(profile.getUsername());
	profile.setImage(profile.getImage());

	return profileRepository.save(profile);
    }

    public Profile createNewProfile(User user, String username) {
	return Profile.builder().user(user).username(username).bio(null).image(DEFAULT_IMAGE_URL).build();
    }

    @Transactional
    public void follow(Profile current, Profile toFollow) {
	current.followProfile(toFollow);
	profileRepository.save(current);
    }

    @Transactional
    public void unfollow(Profile current, Profile toFollow) {
	current.unfollowProfile(toFollow);
	profileRepository.save(current);
    }

    @Transactional
    public Profile favorite(Profile profile, Article article) {
	profile.favoriteArticle(article);
	return profileRepository.save(profile);
    }

    @Transactional
    public Profile unfavorite(Profile profile, Article article) {
	profile.unfavoriteArticle(article);
	return profileRepository.save(profile);
    }

}
