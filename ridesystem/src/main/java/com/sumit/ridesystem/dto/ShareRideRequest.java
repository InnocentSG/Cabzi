package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data

public class ShareRideRequest {

    // =================================
    // PICKUP
    // =================================

    @NotBlank(
            message = "Pickup location is required"
    )

    private String pickup;

    // =================================
    // DESTINATION
    // =================================

    @NotBlank(
            message = "Destination is required"
    )

    private String destination;

    // =================================
    // COORDINATES
    // =================================

    private Double pickupLatitude;

    private Double pickupLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;

    // =================================
    // DISTANCE
    // =================================

    @Positive(
            message = "Distance must be positive"
    )

    private Double distanceKm;

    // =================================
    // VEHICLE
    // =================================

    private String vehicleType;

    // =================================
    // SEATS
    // =================================

    @NotNull(
            message = "Total seats required"
    )

    @Min(
            value = 1,
            message = "Minimum 1 seat required"
    )

    private Integer totalSeats;

    // =================================
    // OPTIONAL FARE OVERRIDE
    // =================================

    @Positive(
            message = "Fare must be positive"
    )

    private Double totalFare;

    // =================================
    // NOTES
    // =================================

    private String notes;
}
