package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data

public class StaffMessageRequest {

    // =================================
    // RECEIVER
    // =================================

    @NotNull(
            message = "Receiver id is required"
    )

    private Long receiverId;

    // =================================
    // MESSAGE
    // =================================

    @NotBlank(
            message = "Message is required"
    )

    private String message;
}