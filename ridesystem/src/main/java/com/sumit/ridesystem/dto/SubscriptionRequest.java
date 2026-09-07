package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data

public class SubscriptionRequest {

    // =================================
    // PLAN NAME
    // =================================

    @NotBlank(
            message = "Plan name is required"
    )

    private String planName;

    // =================================
    // DESCRIPTION
    // =================================

    private String description;

    // =================================
    // AMOUNT
    // =================================

    @NotNull(
            message = "Amount is required"
    )

    @Positive(
            message = "Amount must be positive"
    )

    private Double amount;

    // =================================
    // VALIDITY
    // =================================

    @NotNull(
            message = "Validity days required"
    )

    @Min(
            value = 1,
            message = "Validity must be at least 1 day"
    )

    private Integer validityDays;

    // =================================
    // RIDE LIMIT
    // =================================

    @NotNull(
            message = "Ride limit required"
    )

    @Min(
            value = 1,
            message = "Ride limit must be at least 1"
    )

    private Integer rideLimit;

    // =================================
    // FEATURES
    // =================================

    private Boolean parcelEnabled;

    private Boolean prioritySupport;
}