package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.Ride;
import com.sumit.ridesystem.model.RideStatus;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RideRepository
        extends JpaRepository<Ride, Long> {

    // =================================
    // USER RIDES
    // =================================

    List<Ride>
    findByUserOrderByBookedAtDesc(
            User user
    );

    // =================================
    // DRIVER RIDES
    // =================================

    List<Ride>
    findByDriverOrderByBookedAtDesc(
            Driver driver
    );

    // =================================
    // STATUS RIDES
    // =================================

    List<Ride>
    findByStatus(
            RideStatus status
    );

    // =================================
    // USER STATUS RIDES
    // =================================

    List<Ride>
    findByUserAndStatus(
            User user,
            RideStatus status
    );

    // =================================
    // DRIVER STATUS RIDES
    // =================================

    List<Ride>
    findByDriverAndStatus(
            Driver driver,
            RideStatus status
    );

    // =================================
    // ALL RIDES DESC
    // =================================

    List<Ride>
    findAllByOrderByBookedAtDesc();
}