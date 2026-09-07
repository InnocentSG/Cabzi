package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Payment {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long paymentId;

    // =================================
    // USER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "user_id"
    )

    private User user;

    // =================================
    // RIDE
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "ride_id"
    )

    private Ride ride;

    // =================================
    // PAYMENT DETAILS
    // =================================

    private Double amount;

    private String purpose;

    @Column(unique = true)

    private String transactionId;

    private String paymentMethod;

    // =================================
    // STATUS
    // =================================

    @Enumerated(
            EnumType.STRING
    )

    private PaymentStatus status =
            PaymentStatus.SUCCESS;

    // =================================
    // TIMESTAMP
    // =================================

    @Builder.Default

    private LocalDateTime paidAt =
            LocalDateTime.now();
}