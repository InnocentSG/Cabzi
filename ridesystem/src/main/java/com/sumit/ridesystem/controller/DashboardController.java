package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.service.DashboardService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class DashboardController {

    private final DashboardService
            dashboardService;

    // =================================
    // USER DASHBOARD
    // =================================

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>>
    userDashboard(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                dashboardService.userDashboard(
                        authentication
                )
        );
    }

    // =================================
    // DRIVER DASHBOARD
    // =================================

    @GetMapping("/driver")
    public ResponseEntity<Map<String, Object>>
    driverDashboard(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                dashboardService.driverDashboard(
                        authentication
                )
        );
    }
}