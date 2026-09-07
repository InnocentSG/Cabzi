package com.sumit.ridesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Driver {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long driverId;

    // =================================
    // USER
    // =================================

    @OneToOne

    @JoinColumn(
            name = "user_id"
    )

    @JsonIgnore

    private User user;

    // =================================
    // KYC
    // =================================

    @Column(unique = true)

    private String aadhaarNumber;

    @Column(unique = true)

    private String licenseNumber;

    private String aadhaarImage;

    private String licenseImage;

    // =================================
    // VEHICLE
    // =================================

    @Column(unique = true)

    private String vehicleNumber;

    private String vehicleType;

    // =================================
    // STATUS
    // =================================

    @Enumerated(
            EnumType.STRING
    )

    private DriverStatus status =
            DriverStatus.PENDING;

    // =================================
    // DRIVER DATA
    // =================================

    @Builder.Default

    private Double rating = 5.0;

    @Builder.Default

    private Double totalEarnings = 0.0;

    @Builder.Default

    private Integer totalRides = 0;

    // =================================
    // ONLINE STATUS
    // =================================

    @Builder.Default

    private Boolean online = false;

    // =================================
    // LOCATION
    // =================================

    private Double currentLatitude;

    private Double currentLongitude;

    // =================================
    // CREATED TIME
    // =================================

    @Builder.Default

    private LocalDateTime createdAt =
            LocalDateTime.now();
}