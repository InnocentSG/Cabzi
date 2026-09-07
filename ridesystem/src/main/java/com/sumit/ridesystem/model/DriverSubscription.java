package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "driver_subscriptions")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class DriverSubscription {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long driverSubscriptionId;

    // =================================
    // DRIVER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "driver_id"
    )

    private Driver driver;

    // =================================
    // SUBSCRIPTION
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "subscription_id"
    )

    private Subscription subscription;

    // =================================
    // PAYMENT
    // =================================

    private Double amountPaid;

    // =================================
    // STATUS
    // =================================

    @Builder.Default

    private String status = "REQUESTED";

    // =================================
    // TIMESTAMPS
    // =================================

    @Builder.Default

    private LocalDateTime requestedAt =
            LocalDateTime.now();

    private LocalDateTime approvedAt;

    private LocalDateTime expiryDate;
}