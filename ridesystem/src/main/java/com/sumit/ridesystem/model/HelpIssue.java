package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "help_issues")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class HelpIssue {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long helpIssueId;

    // =================================
    // USER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "user_id"
    )

    private User user;

    // =================================
    // ISSUE
    // =================================

    private String subject;

    @Column(length = 3000)

    private String description;

    // =================================
    // PRIORITY
    // =================================

    private String priority;

    // =================================
    // STATUS
    // =================================

    @Builder.Default

    private String status = "OPEN";

    // =================================
    // ADMIN RESPONSE
    // =================================

    @Column(length = 3000)

    private String response;

    // =================================
    // CREATED
    // =================================

    @Builder.Default

    private LocalDateTime createdAt =
            LocalDateTime.now();

    private LocalDateTime resolvedAt;
}