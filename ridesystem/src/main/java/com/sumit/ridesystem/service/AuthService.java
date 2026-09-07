package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.AuthResponse;
import com.sumit.ridesystem.dto.LoginRequest;
import com.sumit.ridesystem.dto.RegisterRequest;

import com.sumit.ridesystem.model.Role;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.UserRepository;

import com.sumit.ridesystem.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository
            userRepository;

    private final PasswordEncoder
            passwordEncoder;

    private final JwtService
            jwtService;

    private final AuthenticationManager
            authenticationManager;

    // =================================
    // REGISTER
    // =================================

    public AuthResponse register(

            RegisterRequest request
    ) {

        Role role;

        try {

            role = Role.valueOf(

                    request.getRole()
                            .toUpperCase()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Invalid role selected"
            );
        }

        // =================================
        // BLOCK ADMIN REGISTRATION
        // =================================

        if (

                role == Role.ADMIN

                        ||

                        role == Role.STAFF

                        ||

                        role == Role.DRIVER_VERIFIER

        ) {

            throw new RuntimeException(
                    "Invalid role"
            );
        }

        // =================================
        // EMAIL CHECK
        // =================================

        if (

                userRepository
                        .existsByEmailAndRole(

                                request.getEmail(),

                                role
                        )

        ) {

            throw new RuntimeException(
                    "Account already exists for this role"
            );
        }

        // =================================
        // PHONE CHECK
        // =================================

        if (

                userRepository
                        .existsByPhoneAndRole(

                                request.getPhone(),

                                role
                        )

        ) {

            throw new RuntimeException(
                    "Phone already used for this role"
            );
        }

        // =================================
        // CREATE USER
        // =================================

        User user =

                User.builder()

                        .name(
                                request.getName()
                        )

                        .email(
                                request.getEmail()
                        )

                        .phone(
                                request.getPhone()
                        )

                        .password(

                                passwordEncoder.encode(
                                        request.getPassword()
                                )
                        )

                        .role(
                                role
                        )

                        .active(true)

                        .build();

        userRepository.save(user);

        // =================================
        // TOKEN
        // =================================

        String token =

                jwtService.generateToken(
                        user
                );

        // =================================
        // RESPONSE
        // =================================

        return AuthResponse.builder()

                .token(token)

                .userId(
                        user.getUserId()
                )

                .name(
                        user.getName()
                )

                .email(
                        user.getEmail()
                )

                .phone(
                        user.getPhone()
                )

                .role(
                        user.getRole()
                )

                .success(true)

                .message(
                        "Registration successful"
                )

                .build();
    }

    // =================================
    // LOGIN
    // =================================

    public AuthResponse login(

            LoginRequest request
    ) {

        Role role;

        try {

            role = Role.valueOf(

                    request.getRole()
                            .toUpperCase()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Invalid role selected"
            );
        }

        // =================================
        // FIND USER
        // =================================

        User user =

                userRepository
                        .findByEmailAndRole(

                                request.getEmail(),

                                role
                        )

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "No account found for selected role"
                                )
                        );

        // =================================
        // ACTIVE CHECK
        // =================================

        if (

                !Boolean.TRUE.equals(
                        user.getActive()
                )

        ) {

            throw new RuntimeException(
                    "Account is disabled"
            );
        }

        // =================================
        // AUTHENTICATION
        // =================================

        try {

            authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(

                            user.getEmail(),

                            request.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {

            throw new RuntimeException(
                    "Invalid email or password"
            );
        }

        // =================================
        // TOKEN
        // =================================

        String token =

                jwtService.generateToken(
                        user
                );

        // =================================
        // RESPONSE
        // =================================

        return AuthResponse.builder()

                .token(token)

                .userId(
                        user.getUserId()
                )

                .name(
                        user.getName()
                )

                .email(
                        user.getEmail()
                )

                .phone(
                        user.getPhone()
                )

                .role(
                        user.getRole()
                )

                .success(true)

                .message(
                        "Login successful"
                )

                .build();
    }
}