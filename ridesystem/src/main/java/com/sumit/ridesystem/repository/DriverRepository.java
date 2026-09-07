package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.DriverStatus;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DriverRepository
        extends JpaRepository<Driver, Long> {

    // =================================
    // FIND BY USER
    // =================================

    Optional<Driver> findByUser(
            User user
    );

    // =================================
    // FIND BY STATUS
    // =================================

    List<Driver> findByStatus(
            DriverStatus status
    );

    // =================================
    // VEHICLE EXISTS
    // =================================

    boolean existsByVehicleNumber(
            String vehicleNumber
    );

    // =================================
    // VERIFIED DRIVERS
    // =================================

    List<Driver> findByStatusOrderByDriverIdDesc(
            DriverStatus status
    );

    // =================================
    // ALL DRIVERS DESC
    // =================================

    List<Driver> findAllByOrderByDriverIdDesc();
}