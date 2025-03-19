package com.project.blog.domain.service;

import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.blog.domain.repository.ProfileRepository;
import com.project.blog.domain.repository.UserRepository;

import jakarta.persistence.EntityManager;

public class UserServiceTest {
    
    
    @Mock
    private EntityManager entityManager;
    @Mock
    private ProfileService profileService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProfileRepository profileRepository;
}
