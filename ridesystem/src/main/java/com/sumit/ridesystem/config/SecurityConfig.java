package com.sumit.ridesystem.config;

import com.sumit.ridesystem.security.JwtFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor

public class SecurityConfig {

    private final JwtFilter
            jwtFilter;

    @Bean
    public SecurityFilterChain
    securityFilterChain(

            HttpSecurity http
    ) throws Exception {

        http

                // =================================
                // DISABLE CSRF
                // =================================

                .csrf(csrf -> csrf.disable())

                // =================================
                // ENABLE CORS
                // =================================

                .cors(Customizer.withDefaults())

                // =================================
                // AUTHORIZE REQUESTS
                // =================================

                .authorizeHttpRequests(auth -> auth

                        // PREFLIGHT
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()

                        // PUBLIC ROUTES
                        .requestMatchers(

                                "/",

                                "/error",

                                "/auth/**",

                                "/api/auth/**",

                                "/swagger-ui/**",

                                "/swagger-ui.html",

                                "/v3/api-docs/**",

                                "/v3/api-docs",

                                "/api-docs/**",

                                "/api-docs/swagger-config",

                                "/ws/**"

                        ).permitAll()

                        // ADMIN
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasAnyAuthority(
                                "ADMIN"
                        )

                        // STAFF
                        .requestMatchers(
                                "/api/staff/**"
                        ).hasAnyAuthority(
                                "STAFF",
                                "DRIVER_VERIFIER"
                        )

                        // ADMIN / STAFF CHAT
                        .requestMatchers(
                                "/api/chat/**"
                        ).hasAnyAuthority(
                                "ADMIN",
                                "STAFF",
                                "DRIVER_VERIFIER"
                        )

                        // DRIVER
                        .requestMatchers(
                                "/api/drivers/**"
                        ).hasAnyAuthority(
                                "DRIVER",
                                "USER"
                        )

                        // AUTHENTICATED USER
                        .anyRequest().authenticated()
                )

                // =================================
                // STATELESS SESSION
                // =================================

                .sessionManagement(session ->

                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // =================================
                // DISABLE BASIC AUTH
                // =================================

                .httpBasic(httpBasic ->

                        httpBasic.disable()
                )

                // =================================
                // DISABLE FORM LOGIN
                // =================================

                .formLogin(form ->

                        form.disable()
                )

                // =================================
                // JWT FILTER
                // =================================

                .addFilterBefore(

                        jwtFilter,

                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
