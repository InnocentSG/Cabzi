package com.sumit.ridesystem.service;

import com.sumit.ridesystem.model.Notification;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class NotificationService {

    private final NotificationRepository
            notificationRepository;

    private final FirebaseService
            firebaseService;

    // =================================
    // USER NOTIFICATIONS
    // =================================

    public List<Notification>
    getMyNotifications(

            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        return notificationRepository
                .findByUserOrderByCreatedAtDesc(
                        user
                );
    }

    // =================================
    // CREATE NOTIFICATION
    // =================================

    public Notification createNotification(

            User user,

            String message
    ) {

        Notification notification =

                Notification.builder()

                        .user(user)

                        .title(
                                "Notification"
                        )

                        .message(message)

                        .readStatus(false)

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        Notification savedNotification =
                notificationRepository.save(
                notification
        );

        firebaseService.sendNotification(
                user.getFcmToken(),
                savedNotification.getTitle(),
                savedNotification.getMessage()
        );

        return savedNotification;
    }

    // =================================
    // MARK AS READ
    // =================================

    public Notification markAsRead(
            Long notificationId
    ) {

        Notification notification =

                notificationRepository
                        .findById(notificationId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Notification not found"
                                )
                        );

        notification.setReadStatus(true);

        return notificationRepository.save(
                notification
        );
    }

    // =================================
    // MARK ALL AS READ
    // =================================

    public void markAllAsRead(

            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        List<Notification> notifications =

                notificationRepository
                        .findByUserOrderByCreatedAtDesc(
                                user
                        );

        notifications.forEach(notification ->

                notification.setReadStatus(true)
        );

        notificationRepository.saveAll(
                notifications
        );
    }

    // =================================
    // DELETE NOTIFICATION
    // =================================

    public void deleteNotification(
            Long notificationId
    ) {

        notificationRepository
                .deleteById(notificationId);
    }
}
