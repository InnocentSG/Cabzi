package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.ShareRide;
import com.sumit.ridesystem.model.ShareRideStatus;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ShareRideRepository
        extends JpaRepository<ShareRide, Long> {

    // =================================
    // STATUS RIDES
    // =================================

    List<ShareRide>
    findByStatus(
            ShareRideStatus status
    );

    // =================================
    // USER RIDES
    // =================================

    List<ShareRide>
    findByCreatorOrderByCreatedAtDesc(
            User creator
    );

    // =================================
    // STATUS DESC
    // =================================

    List<ShareRide>
    findByStatusOrderByCreatedAtDesc(
            ShareRideStatus status
    );

    // =================================
    // ALL RIDES DESC
    // =================================

    List<ShareRide>
    findAllByOrderByCreatedAtDesc();
}