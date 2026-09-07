package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data

public class ParcelRequest {

    // =================================
    // SENDER
    // =================================

    @NotBlank(
            message = "Sender name is required"
    )

    private String senderName;

    // =================================
    // RECEIVER
    // =================================

    @NotBlank(
            message = "Receiver name is required"
    )

    private String receiverName;

    @NotBlank(
            message = "Receiver phone is required"
    )

    private String receiverPhone;

    // =================================
    // ADDRESS
    // =================================

    @NotBlank(
            message = "Pickup address is required"
    )

    private String pickupAddress;

    @NotBlank(
            message = "Delivery address is required"
    )

    private String deliveryAddress;

    // =================================
    // COORDINATES
    // =================================

    private Double pickupLatitude;

    private Double pickupLongitude;

    private Double deliveryLatitude;

    private Double deliveryLongitude;

    // =================================
    // DISTANCE
    // =================================

    @Positive(
            message = "Distance must be positive"
    )

    private Double distanceKm;

    // =================================
    // WEIGHT
    // =================================

    @NotNull(
            message = "Weight is required"
    )

    @Positive(
            message = "Weight must be positive"
    )

    private Double weightKg;

    // =================================
    // NOTES
    // =================================

    private String notes;
}
