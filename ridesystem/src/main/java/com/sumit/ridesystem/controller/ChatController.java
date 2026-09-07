package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.StaffMessageRequest;

import com.sumit.ridesystem.model.StaffMessage;

import com.sumit.ridesystem.service.ChatService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/people")
    public ResponseEntity<List<Map<String, Object>>>
    people(
            Authentication authentication
    ) {

        return ResponseEntity.ok(
                chatService.participants(authentication)
        );
    }

    @GetMapping("/messages/{userId}")
    public ResponseEntity<List<StaffMessage>>
    conversation(
            Authentication authentication,
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                chatService.conversation(
                        authentication,
                        userId
                )
        );
    }

    @PostMapping("/messages")
    public ResponseEntity<StaffMessage>
    sendMessage(
            Authentication authentication,
            @RequestBody StaffMessageRequest request
    ) {

        return ResponseEntity.ok(
                chatService.sendMessage(
                        authentication,
                        request
                )
        );
    }
}
