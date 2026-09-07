package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import lombok.Data;

@Data

public class RideBookingRequest {

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
    // VEHICLE
    // =================================

    @NotBlank(
            message = "Vehicle type is required"
    )

    private String vehicleType;

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
}
