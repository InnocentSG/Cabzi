package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.DriverVerificationRequest;

import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.StaffLog;
import com.sumit.ridesystem.model.StaffMessage;

import com.sumit.ridesystem.service.DriverService;
import com.sumit.ridesystem.service.StaffService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class StaffController {

    private final DriverService
            driverService;

    private final StaffService
            staffService;

    // =================================
    // PENDING DRIVERS
    // =================================

    @GetMapping("/drivers/pending-kyc")

    public ResponseEntity<List<Driver>>
    pendingDrivers() {

        return ResponseEntity.ok(

                driverService.pendingDrivers()
        );
    }

    // =================================
    // ALL DRIVERS
    // =================================

    @GetMapping("/drivers")

    public ResponseEntity<List<Driver>>
    allDrivers() {

        return ResponseEntity.ok(

                driverService.allDrivers()
        );
    }

    // =================================
    // VERIFY DRIVER
    // =================================

    @PostMapping("/drivers/{driverId}/verify")

    public ResponseEntity<Driver>
    verifyDriver(

            @PathVariable Long driverId,

            @RequestBody
            DriverVerificationRequest request
    ) {

        return ResponseEntity.ok(

                driverService.verifyDriver(
                        driverId,
                        request
                )
        );
    }

    // =================================
    // REJECT DRIVER
    // =================================

    @PostMapping("/drivers/{driverId}/reject")

    public ResponseEntity<Driver>
    rejectDriver(

            @PathVariable Long driverId,

            @RequestBody
            DriverVerificationRequest request
    ) {

        return ResponseEntity.ok(

                driverService.rejectDriver(
                        driverId,
                        request
                )
        );
    }

    // =================================
    // STAFF LOGS
    // =================================

    @GetMapping("/logs")

    public ResponseEntity<List<StaffLog>>
    logs() {

        return ResponseEntity.ok(

                staffService.logs()
        );
    }

    // =================================
    // STAFF MESSAGES
    // =================================

    @GetMapping("/all-messages")

    public ResponseEntity<List<StaffMessage>>
    messages() {

        return ResponseEntity.ok(

                staffService.messages()
        );
    }

}
