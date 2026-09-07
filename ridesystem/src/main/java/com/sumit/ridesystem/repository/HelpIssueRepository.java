package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.HelpIssue;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface HelpIssueRepository
        extends JpaRepository<HelpIssue, Long> {

    // =================================
    // USER ISSUES
    // =================================

    List<HelpIssue>
    findByUserOrderByCreatedAtDesc(
            User user
    );

    // =================================
    // STATUS ISSUES
    // =================================

    List<HelpIssue>
    findByStatus(
            String status
    );

    // =================================
    // ALL ISSUES DESC
    // =================================

    List<HelpIssue>
    findAllByOrderByCreatedAtDesc();
}