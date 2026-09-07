package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data

public class VehicleUpdateRequest {

    // =================================
    // VEHICLE TYPE
    // =================================

    @NotBlank(
            message = "Vehicle type is required"
    )

    private String vehicleType;

    // =================================
    // VEHICLE NAME
    // =================================

    @NotBlank(
            message = "Vehicle name is required"
    )

    private String vehicleName;

    // =================================
    // VEHICLE NUMBER
    // =================================

    @NotBlank(
            message = "Vehicle number is required"
    )

    private String vehicleNumber;

    // =================================
    // VEHICLE COLOR
    // =================================

    private String vehicleColor;
}