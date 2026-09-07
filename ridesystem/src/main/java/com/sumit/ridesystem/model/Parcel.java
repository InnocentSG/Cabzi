package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "parcels")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Parcel {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long parcelId;

    // =================================
    // USER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "user_id"
    )

    private User user;

    // =================================
    // PARCEL DETAILS
    // =================================

    private String senderName;

    private String receiverName;

    private String receiverPhone;

    private String pickupAddress;

    private String deliveryAddress;

    private Double pickupLatitude;

    private Double pickupLongitude;

    private Double deliveryLatitude;

    private Double deliveryLongitude;

    private Double distanceKm;

    private Double weightKg;

    private Double amount;

    // =================================
    // TRACKING
    // =================================

    private String trackingCode;

    // =================================
    // DELIVERY NOTES
    // =================================

    @Column(length = 1000)

    private String notes;

    // =================================
    // STATUS
    // =================================

    @Builder.Default

    private String status = "PENDING";

    // =================================
    // TIMESTAMP
    // =================================

    @Builder.Default

    private LocalDateTime createdAt =
            LocalDateTime.now();

    private LocalDateTime deliveredAt;
}
