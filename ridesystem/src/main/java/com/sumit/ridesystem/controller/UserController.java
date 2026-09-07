package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // =========================
    // Current Logged User
    // =========================

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(
            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }

    // =========================
    // Update Profile
    // =========================

    @PutMapping("/me")
    public ResponseEntity<User> updateProfile(

            Authentication authentication,

            @RequestBody User updatedUser
    ) {

        User currentUser =
                (User) authentication.getPrincipal();

        currentUser.setName(
                updatedUser.getName()
        );

        currentUser.setEmail(
                updatedUser.getEmail()
        );

        currentUser.setPhone(
                updatedUser.getPhone()
        );

        currentUser.setFcmToken(
                updatedUser.getFcmToken()
        );

        // Update password only if entered
        if (
                updatedUser.getPassword() != null
                        &&
                        !updatedUser.getPassword().isBlank()
        ) {

            currentUser.setPassword(

                    passwordEncoder.encode(
                            updatedUser.getPassword()
                    )
            );
        }

        userRepository.save(currentUser);

        return ResponseEntity.ok(currentUser);
    }
}
