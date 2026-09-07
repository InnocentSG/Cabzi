package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.ShareRide;
import com.sumit.ridesystem.model.ShareRideJoin;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ShareRideJoinRepository
        extends JpaRepository<ShareRideJoin, Long> {

    // =================================
    // RIDE MEMBERS
    // =================================

    List<ShareRideJoin>
    findByShareRide(
            ShareRide shareRide
    );

    // =================================
    // USER JOINED
    // =================================

    boolean existsByShareRideAndUser(

            ShareRide shareRide,

            User user
    );

    // =================================
    // USER JOINS
    // =================================

    List<ShareRideJoin>
    findByUserOrderByJoinedAtDesc(
            User user
    );

    // =================================
    // TOTAL MEMBERS
    // =================================

    long countByShareRide(
            ShareRide shareRide
    );
}