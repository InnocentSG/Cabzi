package com.sumit.ridesystem.config;

import com.sumit.ridesystem.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor

public class ApplicationConfig {

    private final CustomUserDetailsService
            userDetailsService;

    private final PasswordEncoder
            passwordEncoder;

    // =========================
    // Authentication Provider
    // =========================

    @Bean
    public AuthenticationProvider
    authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                userDetailsService
        );

        provider.setPasswordEncoder(
                passwordEncoder
        );

        return provider;
    }

    // =========================
    // Authentication Manager
    // =========================

    @Bean
    public AuthenticationManager
    authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}