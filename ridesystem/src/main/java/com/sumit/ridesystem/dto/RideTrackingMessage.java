package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data

public class RideTrackingMessage {

    // =================================
    // RIDE
    // =================================

    @NotNull(
            message = "Ride id is required"
    )

    private Long rideId;

    // =================================
    // LATITUDE
    // =================================

    @NotNull(
            message = "Latitude is required"
    )

    private Double latitude;

    // =================================
    // LONGITUDE
    // =================================

    @NotNull(
            message = "Longitude is required"
    )

    private Double longitude;

    // =================================
    // ETA
    // =================================

    @NotNull(
            message = "ETA is required"
    )

    private Integer eta;
}