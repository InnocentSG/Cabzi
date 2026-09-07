package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.Notification;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    // =================================
    // USER NOTIFICATIONS
    // =================================

    List<Notification>
    findByUserOrderByCreatedAtDesc(
            User user
    );

    // =================================
    // UNREAD NOTIFICATIONS
    // =================================

    List<Notification>
    findByUserAndReadStatusFalseOrderByCreatedAtDesc(
            User user
    );

    // =================================
    // ALL NOTIFICATIONS DESC
    // =================================

    List<Notification>
    findAllByOrderByCreatedAtDesc();
}