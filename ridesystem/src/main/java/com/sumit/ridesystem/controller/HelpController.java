package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.model.HelpIssue;

import com.sumit.ridesystem.service.HelpService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

@RequestMapping("/api/help")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class HelpController {

    private final HelpService
            helpService;

    // =================================
    // CREATE ISSUE
    // =================================

    @PostMapping
    public ResponseEntity<HelpIssue>
    createIssue(

            Authentication authentication,

            @RequestBody
            HelpIssue request
    ) {

        return ResponseEntity.ok(

                helpService.createIssue(
                        authentication,
                        request
                )
        );
    }

    // =================================
    // MY ISSUES
    // =================================

    @GetMapping("/my")
    public ResponseEntity<List<HelpIssue>>
    myIssues(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                helpService.myIssues(
                        authentication
                )
        );
    }

    // =================================
    // ALL ISSUES
    // =================================

    @GetMapping
    public ResponseEntity<List<HelpIssue>>
    allIssues() {

        return ResponseEntity.ok(
                helpService.allIssues()
        );
    }

    // =================================
    // UPDATE STATUS
    // =================================

    @PatchMapping("/{issueId}/status")
    public ResponseEntity<HelpIssue>
    updateStatus(

            @PathVariable Long issueId,

            @RequestBody
            Map<String, String> payload
    ) {

        return ResponseEntity.ok(

                helpService.updateStatus(

                        issueId,

                        payload.get("status")
                )
        );
    }
}