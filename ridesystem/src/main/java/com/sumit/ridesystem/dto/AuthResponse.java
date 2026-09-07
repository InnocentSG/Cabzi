package com.sumit.ridesystem.dto;

import com.sumit.ridesystem.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AuthResponse {

    // =================================
    // JWT TOKEN
    // =================================

    private String token;

    // =================================
    // USER DETAILS
    // =================================

    private Long userId;

    private String name;

    private String email;

    private String phone;

    private Role role;

    // =================================
    // STATUS
    // =================================

    private Boolean success;

    private String message;
}