package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "share_rides")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ShareRide {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long shareRideId;

    // =================================
    // CREATOR
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "creator_id"
    )

    private User creator;

    // =================================
    // ROUTE
    // =================================

    private String pickup;

    private String destination;

    private Double pickupLatitude;

    private Double pickupLongitude;

    private Double destinationLatitude;

    private Double destinationLongitude;

    private Double distanceKm;

    private String vehicleType;

    // =================================
    // SEATS
    // =================================

    private Integer totalSeats;

    private Integer availableSeats;

    // =================================
    // PRICING
    // =================================

    private Double totalFare;

    private Double perSeatFare;

    // =================================
    // RIDE TIME
    // =================================

    private LocalDateTime rideTime;

    // =================================
    // NOTES
    // =================================

    @Column(length = 1000)

    private String notes;

    // =================================
    // STATUS
    // =================================

    @Enumerated(
            EnumType.STRING
    )

    private ShareRideStatus status =
            ShareRideStatus.OPEN;

    // =================================
    // TIMESTAMP
    // =================================

    @Builder.Default

    private LocalDateTime createdAt =
            LocalDateTime.now();
}
