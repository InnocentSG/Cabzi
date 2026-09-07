package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.model.Notification;

import com.sumit.ridesystem.service.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class NotificationController {

    private final NotificationService
            notificationService;

    // =================================
    // MY NOTIFICATIONS
    // =================================

    @GetMapping

    public ResponseEntity<List<Notification>>
    getMyNotifications(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                notificationService
                        .getMyNotifications(
                                authentication
                        )
        );
    }

    // =================================
    // MARK AS READ
    // =================================

    @PatchMapping("/{notificationId}/read")

    public ResponseEntity<Notification>
    markAsRead(

            @PathVariable Long notificationId
    ) {

        return ResponseEntity.ok(

                notificationService
                        .markAsRead(
                                notificationId
                        )
        );
    }

    // =================================
    // MARK ALL AS READ
    // =================================

    @PatchMapping("/read-all")

    public ResponseEntity<String>
    markAllAsRead(

            Authentication authentication
    ) {

        notificationService
                .markAllAsRead(
                        authentication
                );

        return ResponseEntity.ok(
                "All notifications marked as read"
        );
    }

    // =================================
    // DELETE NOTIFICATION
    // =================================

    @DeleteMapping("/{notificationId}")

    public ResponseEntity<String>
    deleteNotification(

            @PathVariable Long notificationId
    ) {

        notificationService
                .deleteNotification(
                        notificationId
                );

        return ResponseEntity.ok(
                "Notification deleted successfully"
        );
    }
}