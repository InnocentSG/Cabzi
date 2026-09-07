package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data

public class RegisterRequest {

    // =================================
    // NAME
    // =================================

    @NotBlank(
            message = "Name is required"
    )

    private String name;

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
    // PHONE
    // =================================

    @Pattern(

            regexp = "^[0-9]{10}$",

            message = "Phone must be 10 digits"
    )

    private String phone;

    // =================================
    // PASSWORD
    // =================================

    @Size(

            min = 6,

            message = "Password must be at least 6 characters"
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