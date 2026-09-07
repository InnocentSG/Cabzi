package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Booking {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long id;

    // =================================
    // USER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "user_id"
    )

    private User user;

    // =================================
    // CUSTOMER
    // =================================

    private String customer;

    // =================================
    // LOCATION
    // =================================

    private String pickup;

    private String dropLocation;

    // =================================
    // RIDE DETAILS
    // =================================

    private String rideType;

    private String driverName;

    private Double amount;

    // =================================
    // STATUS
    // =================================

    private String status;

    // =================================
    // TIMESTAMP
    // =================================

    @Builder.Default

    private LocalDateTime bookedAt =
            LocalDateTime.now();
}