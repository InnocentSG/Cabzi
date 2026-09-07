package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Notification {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long notificationId;

    // =================================
    // USER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "user_id"
    )

    private User user;

    // =================================
    // TITLE
    // =================================

    private String title;

    // =================================
    // MESSAGE
    // =================================

    @Column(length = 1000)

    private String message;

    // =================================
    // TYPE
    // =================================

    private String type;

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