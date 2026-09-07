package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "staff_logs")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class StaffLog {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long logId;

    // =================================
    // STAFF
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "staff_id"
    )

    private User staff;

    // =================================
    // ACTION
    // =================================

    private String action;

    @Column(length = 1000)

    private String details;

    // =================================
    // MODULE
    // =================================

    private String module;

    // =================================
    // TIMESTAMP
    // =================================

    @Builder.Default

    private LocalDateTime createdAt =
            LocalDateTime.now();
}