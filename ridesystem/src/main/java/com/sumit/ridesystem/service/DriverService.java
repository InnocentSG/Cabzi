package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.DriverKycRequest;
import com.sumit.ridesystem.dto.DriverVerificationRequest;
import com.sumit.ridesystem.dto.VehicleUpdateRequest;

import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.DriverStatus;
import com.sumit.ridesystem.model.Role;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.DriverRepository;
import com.sumit.ridesystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DriverService {

    private final DriverRepository
            driverRepository;

    private final UserRepository
            userRepository;

    private final NotificationService
            notificationService;

    // =================================
    // APPLY DRIVER
    // =================================

    public Driver applyDriver(

            Authentication authentication,

            DriverKycRequest request
    ) {

        User user =
                (User) authentication.getPrincipal();

        if (

                driverRepository
                        .findByUser(user)
                        .isPresent()

        ) {

            throw new RuntimeException(
                    "Driver profile already exists"
            );
        }

        if (

                driverRepository
                        .existsByVehicleNumber(
                                request.getVehicleNumber()
                        )

        ) {

            throw new RuntimeException(
                    "Vehicle number already exists"
            );
        }

        Driver driver =

                Driver.builder()

                        .user(user)

                        .aadhaarNumber(
                                request.getAadhaarNumber()
                        )

                        .licenseNumber(
                                request.getLicenseNumber()
                        )

                        .aadhaarImage(
                                request.getAadhaarImage()
                        )

                        .licenseImage(
                                request.getLicenseImage()
                        )

                        .vehicleNumber(
                                request.getVehicleNumber()
                        )

                        .vehicleType(
                                request.getVehicleType()
                        )

                        .status(
                                DriverStatus.PENDING
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        Driver savedDriver =
                driverRepository.save(driver);

        notificationService.createNotification(
                user,
                "Your driver application submitted successfully"
        );

        return savedDriver;
    }

    // =================================
    // MY DRIVER PROFILE
    // =================================

    public Driver myDriverProfile(

            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        return driverRepository

                .findByUser(user)

                .orElseThrow(() ->

                        new RuntimeException(
                                "Driver profile not found"
                        )
                );
    }

    // =================================
    // UPDATE VEHICLE
    // =================================

    public Driver updateVehicle(

            Authentication authentication,

            VehicleUpdateRequest request
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

        driver.setVehicleType(
                request.getVehicleType()
        );

        driver.setVehicleNumber(
                request.getVehicleNumber()
        );

        return driverRepository.save(driver);
    }

    // =================================
    // VERIFY DRIVER
    // =================================

    public Driver verifyDriver(

            Long driverId,

            DriverVerificationRequest request
    ) {

        Driver driver =

                driverRepository
                        .findById(driverId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Driver not found"
                                )
                        );

        User user =
                driver.getUser();

        if (

                Boolean.TRUE.equals(
                        request.getApproved()
                )

        ) {

            driver.setStatus(
                    DriverStatus.APPROVED
            );

            user.setRole(
                    Role.DRIVER
            );

            userRepository.save(user);

            notificationService.createNotification(
                    user,
                    "Your driver account has been approved"
            );

        } else {

            driver.setStatus(
                    DriverStatus.REJECTED
            );

            notificationService.createNotification(
                    user,
                    request.getMessage() != null
                            ? request.getMessage()
                            : "Your driver application has been rejected"
            );
        }

        return driverRepository.save(driver);
    }

    // =================================
    // REJECT DRIVER
    // =================================

    public Driver rejectDriver(

            Long driverId,

            DriverVerificationRequest request
    ) {

        request.setApproved(false);

        return verifyDriver(
                driverId,
                request
        );
    }

    // =================================
    // PENDING DRIVERS
    // =================================

    public List<Driver>
    pendingDrivers() {

        return driverRepository

                .findByStatusOrderByDriverIdDesc(
                        DriverStatus.PENDING
                );
    }

    // =================================
    // ALL DRIVERS
    // =================================

    public List<Driver>
    allDrivers() {

        return driverRepository
                .findAllByOrderByDriverIdDesc();
    }

}
