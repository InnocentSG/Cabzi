package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.DriverKycRequest;
import com.sumit.ridesystem.dto.VehicleUpdateRequest;

import com.sumit.ridesystem.model.Driver;

import com.sumit.ridesystem.service.DriverService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/drivers")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class DriverController {

    private final DriverService
            driverService;

    // =================================
    // APPLY DRIVER
    // =================================

    @PostMapping("/apply")

    public ResponseEntity<Driver>
    applyDriver(

            Authentication authentication,

            @RequestBody
            DriverKycRequest request
    ) {

        return ResponseEntity.ok(

                driverService.applyDriver(
                        authentication,
                        request
                )
        );
    }

    // =================================
    // DRIVER PROFILE
    // =================================

    @GetMapping("/me")

    public ResponseEntity<Driver>
    myDriverProfile(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                driverService.myDriverProfile(
                        authentication
                )
        );
    }

    // =================================
    // UPDATE VEHICLE
    // =================================

    @PutMapping("/vehicle")

    public ResponseEntity<Driver>
    updateVehicle(

            Authentication authentication,

            @RequestBody
            VehicleUpdateRequest request
    ) {

        return ResponseEntity.ok(

                driverService.updateVehicle(
                        authentication,
                        request
                )
        );
    }

    // =================================
    // ALL DRIVERS
    // =================================

    @GetMapping

    public ResponseEntity<List<Driver>>
    allDrivers() {

        return ResponseEntity.ok(

                driverService.allDrivers()
        );
    }
}
