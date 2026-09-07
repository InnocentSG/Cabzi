package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data

public class DriverKycRequest {

    // =================================
    // AADHAAR
    // =================================

    @NotBlank(
            message = "Aadhaar number is required"
    )

    private String aadhaarNumber;

    // =================================
    // LICENSE
    // =================================

    @NotBlank(
            message = "License number is required"
    )

    private String licenseNumber;

    // =================================
    // VEHICLE
    // =================================

    @NotBlank(
            message = "Vehicle number is required"
    )

    private String vehicleNumber;

    @NotBlank(
            message = "Vehicle type is required"
    )

    private String vehicleType;

    // =================================
    // DOCUMENTS
    // =================================

    private String aadhaarImage;

    private String licenseImage;
}