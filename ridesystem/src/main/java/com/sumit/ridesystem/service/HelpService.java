package com.sumit.ridesystem.service;

import com.sumit.ridesystem.model.HelpIssue;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.HelpIssueRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor

public class HelpService {

    private final HelpIssueRepository
            helpIssueRepository;

    private final NotificationService
            notificationService;

    // =================================
    // CREATE HELP ISSUE
    // =================================

    public HelpIssue createIssue(

            Authentication authentication,

            HelpIssue request
    ) {

        User user =
                (User) authentication.getPrincipal();

        HelpIssue issue =

                HelpIssue.builder()

                        .user(user)

                        .subject(
                                request.getSubject()
                        )

                        .description(
                                request.getDescription()
                        )

                        .priority(
                                request.getPriority()
                        )

                        .status(
                                "OPEN"
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        HelpIssue savedIssue =
                helpIssueRepository.save(issue);

        notificationService.createNotification(
                user,
                "Your support request submitted successfully"
        );

        return savedIssue;
    }

    // =================================
    // MY ISSUES
    // =================================

    public List<HelpIssue>
    myIssues(

            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        return helpIssueRepository

                .findByUserOrderByCreatedAtDesc(
                        user
                );
    }

    // =================================
    // ALL ISSUES
    // =================================

    public List<HelpIssue>
    allIssues() {

        return helpIssueRepository

                .findAllByOrderByCreatedAtDesc();
    }

    // =================================
    // UPDATE STATUS
    // =================================

    public HelpIssue updateStatus(

            Long issueId,

            String status
    ) {

        HelpIssue issue =

                helpIssueRepository
                        .findById(issueId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Issue not found"
                                )
                        );

        issue.setStatus(status);

        if (

                "RESOLVED".equalsIgnoreCase(
                        status
                )

        ) {

            issue.setResolvedAt(
                    LocalDateTime.now()
            );
        }

        return helpIssueRepository.save(issue);
    }
}
