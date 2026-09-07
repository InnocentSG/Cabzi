package com.sumit.ridesystem.service;

import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.Ride;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.DriverRepository;
import com.sumit.ridesystem.repository.RideRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class DashboardService {

    private final RideRepository rideRepository;

    private final DriverRepository driverRepository;

    // =================================
    // USER DASHBOARD
    // =================================

    public Map<String, Object>
    userDashboard(
            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        List<Ride> rides =
                rideRepository
                        .findByUserOrderByBookedAtDesc(
                                user
                        );

        Map<String, Object> dashboard =
                new HashMap<>();

        dashboard.put(
                "totalRides",
                rides.size()
        );

        dashboard.put(
                "recentRides",
                rides
        );

        long completedRides =
                rides.stream()
                        .filter(ride ->

                                ride.getStatus() != null

                                        &&

                                        ride.getStatus()
                                                .name()
                                                .equals("COMPLETED")
                        )
                        .count();

        dashboard.put(
                "completedRides",
                completedRides
        );

        return dashboard;
    }

    // =================================
    // DRIVER DASHBOARD
    // =================================

    public Map<String, Object>
    driverDashboard(
            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        Driver driver =
                driverRepository
                        .findByUser(user)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Driver not found"
                                )
                        );

        List<Ride> rides =
                rideRepository
                        .findByDriverOrderByBookedAtDesc(
                                driver
                        );

        Map<String, Object> dashboard =
                new HashMap<>();

        dashboard.put(
                "totalRides",
                rides.size()
        );

        dashboard.put(
                "recentRides",
                rides
        );

        long completedRides =
                rides.stream()
                        .filter(ride ->

                                ride.getStatus() != null

                                        &&

                                        ride.getStatus()
                                                .name()
                                                .equals("COMPLETED")
                        )
                        .count();

        dashboard.put(
                "completedRides",
                completedRides
        );

        dashboard.put(
                "rating",
                driver.getRating()
        );

        dashboard.put(
                "earnings",
                driver.getTotalEarnings()
        );

        return dashboard;
    }
}