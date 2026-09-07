package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "staff_messages")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class StaffMessage {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long messageId;

    // =================================
    // SENDER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "sender_id"
    )

    private User sender;

    // =================================
    // RECEIVER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "receiver_id"
    )

    private User receiver;

    // =================================
    // MESSAGE
    // =================================

    @Column(length = 2000)

    private String message;

    // =================================
    // STATUS
    // =================================

    @Builder.Default

    private Boolean readStatus = false;

    // =================================
    // TIMESTAMP
    // =================================

    @Builder.Default

    private LocalDateTime createdAt =
            LocalDateTime.now();
}