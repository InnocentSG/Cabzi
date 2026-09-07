package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data

public class PaymentRequest {

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
    // PURPOSE
    // =================================

    @NotBlank(
            message = "Purpose is required"
    )

    private String purpose;

    // =================================
    // RIDE
    // =================================

    private Long rideId;

    // =================================
    // PAYMENT METHOD
    // =================================

    private String paymentMethod;
}