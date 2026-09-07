package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.RideBookingRequest;
import com.sumit.ridesystem.dto.RideRatingRequest;

import com.sumit.ridesystem.model.Ride;

import com.sumit.ridesystem.service.RideService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rides")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class RideController {

    private final RideService
            rideService;

    // =================================
    // BOOK RIDE
    // =================================

    @PostMapping

    public ResponseEntity<Ride>
    bookRide(

            Authentication authentication,

            @RequestBody
            RideBookingRequest request
    ) {

        return ResponseEntity.ok(

                rideService.bookRide(
                        authentication,
                        request
                )
        );
    }

    @PostMapping("/fare")

    public ResponseEntity<Ride>
    estimateFare(

            @RequestBody
            RideBookingRequest request
    ) {

        return ResponseEntity.ok(

                rideService.estimateRide(
                        request
                )
        );
    }

    // =================================
    // MY RIDES
    // =================================

    @GetMapping("/my")

    public ResponseEntity<List<Ride>>
    myRides(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                rideService.myRides(
                        authentication
                )
        );
    }

    // =================================
    // RATE RIDE
    // =================================

    @PostMapping("/{rideId}/rate")

    public ResponseEntity<Ride>
    rateRide(

            @PathVariable Long rideId,

            @RequestBody
            RideRatingRequest request
    ) {

        return ResponseEntity.ok(

                rideService.rateRide(
                        rideId,
                        request
                )
        );
    }

    // =================================
    // COMPLETE RIDE
    // =================================

    @PatchMapping("/{rideId}/complete")

    public ResponseEntity<Ride>
    completeRide(

            @PathVariable Long rideId
    ) {

        return ResponseEntity.ok(

                rideService.completeRide(
                        rideId
                )
        );
    }

    // =================================
    // CANCEL RIDE
    // =================================

    @PatchMapping("/{rideId}/cancel")

    public ResponseEntity<Ride>
    cancelRide(

            @PathVariable Long rideId
    ) {

        return ResponseEntity.ok(

                rideService.cancelRide(
                        rideId
                )
        );
    }

    // =================================
    // UPDATE TRACKING
    // =================================

    @PatchMapping("/{rideId}/tracking")

    public ResponseEntity<Ride>
    updateTracking(

            @PathVariable Long rideId,

            @RequestBody
            Map<String, Object> payload
    ) {

        Double latitude =

                Double.parseDouble(

                        payload.get("latitude")
                                .toString()
                );

        Double longitude =

                Double.parseDouble(

                        payload.get("longitude")
                                .toString()
                );

        Integer eta =

                Integer.parseInt(

                        payload.get("eta")
                                .toString()
                );

        return ResponseEntity.ok(

                rideService.updateTracking(
                        rideId,
                        latitude,
                        longitude,
                        eta
                )
        );
    }
}
