package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data

public class LoginRequest {

    // =================================
    // EMAIL
    // =================================

    @Email(
            message = "Invalid email format"
    )

    @NotBlank(
            message = "Email is required"
    )

    private String email;

    // =================================
    // PASSWORD
    // =================================

    @NotBlank(
            message = "Password is required"
    )

    private String password;

    // =================================
    // ROLE
    // =================================

    @NotBlank(
            message = "Role is required"
    )

    private String role;
}