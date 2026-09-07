package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Subscription {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long subscriptionId;

    // =================================
    // PLAN DETAILS
    // =================================

    private String planName;

    @Column(length = 1000)

    private String description;

    private Double amount;

    private Integer validityDays;

    private Integer rideLimit;

    // =================================
    // FEATURES
    // =================================

    private Boolean parcelEnabled;

    private Boolean prioritySupport;

    // =================================
    // STATUS
    // =================================

    @Builder.Default

    private Boolean active = true;

    // =================================
    // TIMESTAMP
    // =================================

    @Builder.Default

    private LocalDateTime createdAt =
            LocalDateTime.now();
}