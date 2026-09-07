package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.StaffMessageRequest;

import com.sumit.ridesystem.model.StaffLog;
import com.sumit.ridesystem.model.StaffMessage;

import com.sumit.ridesystem.service.StaffService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class StaffMessageController {

    private final StaffService
            staffService;

    // =================================
    // MY LOGS
    // =================================

    @GetMapping("/my-logs")
    public ResponseEntity<List<StaffLog>>
    myLogs(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                staffService.myLogs(
                        authentication
                )
        );
    }

    // =================================
    // MY MESSAGES
    // =================================

    @GetMapping("/messages")
    public ResponseEntity<List<StaffMessage>>
    myMessages(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                staffService.myMessages(
                        authentication
                )
        );
    }

    // =================================
    // SEND MESSAGE
    // =================================

    @PostMapping("/messages")
    public ResponseEntity<StaffMessage>
    sendMessage(

            Authentication authentication,

            @RequestBody
            StaffMessageRequest request
    ) {

        return ResponseEntity.ok(

                staffService.sendMessage(
                        authentication,
                        request
                )
        );
    }
}