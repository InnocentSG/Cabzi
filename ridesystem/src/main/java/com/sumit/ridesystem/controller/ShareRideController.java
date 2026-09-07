package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.ShareRideRequest;

import com.sumit.ridesystem.model.ShareRide;

import com.sumit.ridesystem.service.ShareRideService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/share-rides")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class ShareRideController {

    private final ShareRideService
            shareRideService;

    // =================================
    // OPEN SHARE RIDES
    // =================================

    @GetMapping

    public ResponseEntity<List<ShareRide>>
    getOpenRides() {

        return ResponseEntity.ok(

                shareRideService.getOpenRides()
        );
    }

    // =================================
    // CREATE SHARE RIDE
    // =================================

    @PostMapping

    public ResponseEntity<ShareRide>
    createRide(

            Authentication authentication,

            @RequestBody
            ShareRideRequest request
    ) {

        return ResponseEntity.ok(

                shareRideService.createRide(
                        authentication,
                        request
                )
        );
    }

    @PostMapping("/fare")

    public ResponseEntity<ShareRide>
    estimateFare(

            @RequestBody
            ShareRideRequest request
    ) {

        return ResponseEntity.ok(

                shareRideService.estimateRide(
                        request
                )
        );
    }

    // =================================
    // JOIN SHARE RIDE
    // =================================

    @PostMapping("/{rideId}/join")

    public ResponseEntity<ShareRide>
    joinRide(

            @PathVariable Long rideId,

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                shareRideService.joinRide(
                        rideId,
                        authentication
                )
        );
    }

    // =================================
    // START PICKUP
    // =================================

    @PostMapping("/{rideId}/start-pickup")

    public ResponseEntity<ShareRide>
    startPickup(

            @PathVariable Long rideId
    ) {

        return ResponseEntity.ok(

                shareRideService.startPickup(
                        rideId
                )
        );
    }

    // =================================
    // CANCEL SHARE RIDE
    // =================================

    @PatchMapping("/{rideId}/cancel")

    public ResponseEntity<ShareRide>
    cancelRide(

            @PathVariable Long rideId
    ) {

        return ResponseEntity.ok(

                shareRideService.cancelRide(
                        rideId
                )
        );
    }
}
