package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data

public class DriverVerificationRequest {

    // =================================
    // APPROVAL STATUS
    // =================================

    @NotNull(
            message = "Approval status is required"
    )

    private Boolean approved;

    // =================================
    // OPTIONAL MESSAGE
    // =================================

    private String message;
}