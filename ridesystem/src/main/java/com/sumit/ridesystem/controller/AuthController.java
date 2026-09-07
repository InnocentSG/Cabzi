package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.AuthResponse;
import com.sumit.ridesystem.dto.LoginRequest;
import com.sumit.ridesystem.dto.RegisterRequest;

import com.sumit.ridesystem.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class AuthController {

    private final AuthService
            authService;

    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")

    public ResponseEntity<AuthResponse>
    register(

            @RequestBody
            RegisterRequest request
    ) {

        return ResponseEntity

                .status(HttpStatus.CREATED)

                .body(

                        authService.register(
                                request
                        )
                );
    }

    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")

    public ResponseEntity<AuthResponse>
    login(

            @RequestBody
            LoginRequest request
    ) {

        return ResponseEntity.ok(

                authService.login(
                        request
                )
        );
    }

    // =========================
    // HEALTH CHECK
    // =========================

    @GetMapping("/health")

    public ResponseEntity<String>
    health() {

        return ResponseEntity.ok(
                "Auth service running"
        );
    }
}