package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rides")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Ride {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long rideId;

    // =================================
    // USER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "user_id"
    )

    private User user;

    // =================================
    // DRIVER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "driver_id"
    )

    private Driver driver;

    // =================================
    // LOCATION
    // =================================

    private String pickup;

    private String destination;

    private Double pickupLatitude;

    private Double pickupLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;

    // =================================
    // RIDE DETAILS
    // =================================

    private Double fare;

    private Double distanceKm;

    private String vehicleType;

    // =================================
    // TRACKING
    // =================================

    private Double driverLatitude;

    private Double driverLongitude;

    private Integer etaMinutes;

    // =================================
    // OTP
    // =================================

    private String rideOtp;

    // =================================
    // PAYMENT
    // =================================

    private Boolean paid = false;

    // =================================
    // RATING
    // =================================

    private Integer rating;

    @Column(length = 1000)

    private String review;

    // =================================
    // STATUS
    // =================================

    @Enumerated(
            EnumType.STRING
    )

    private RideStatus status =
            RideStatus.PENDING;

    // =================================
    // TIMESTAMPS
    // =================================

    @Builder.Default

    private LocalDateTime bookedAt =
            LocalDateTime.now();

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private LocalDateTime cancelledAt;
}
