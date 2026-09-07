package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.RideBookingRequest;
import com.sumit.ridesystem.dto.RideRatingRequest;
import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.DriverStatus;
import com.sumit.ridesystem.model.Ride;
import com.sumit.ridesystem.model.RideStatus;
import com.sumit.ridesystem.model.User;
import com.sumit.ridesystem.repository.DriverRepository;
import com.sumit.ridesystem.repository.RideRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;

    private final DriverRepository driverRepository;

    private final NotificationService notificationService;

    private final RoutingService routingService;

    private final FareService fareService;


    // =================================
    // BOOK RIDE
    // =================================

    public Ride bookRide(
            Authentication authentication,
            RideBookingRequest request
    ) {

        User user =
                (User) authentication.getPrincipal();


        // =================================
        // VALIDATE LOCATIONS
        // =================================

        if (
                request.getPickupLatitude() == null
                        || request.getPickupLongitude() == null
                        || request.getDestinationLatitude() == null
                        || request.getDestinationLongitude() == null
        ) {

            throw new RuntimeException(
                    "Pickup and destination coordinates are required"
            );
        }


        // =================================
        // FIND DRIVER
        // =================================

        Driver driver =
                driverRepository
                        .findAll()
                        .stream()
                        .filter(d ->
                                d.getStatus()
                                        == DriverStatus.APPROVED
                        )
                        .findFirst()
                        .orElse(null);


        // =================================
        // REAL ROAD ROUTE
        // =================================

        RoutingService.RouteResult route =
                routingService.calculateRoute(
                        request.getPickupLatitude(),
                        request.getPickupLongitude(),
                        request.getDestinationLatitude(),
                        request.getDestinationLongitude()
                );


        // =================================
        // REAL ROAD DISTANCE
        // =================================

        double distance =
                route.distanceKm();


        // =================================
        // REAL ROAD ETA
        // =================================

        int etaMinutes =
                route.durationMinutes();


        // =================================
        // CABZI FARE
        // =================================

        double fare =
                fareService.rideFare(
                        request.getVehicleType(),
                        distance
                );


        // =================================
        // CREATE RIDE
        // =================================

        Ride ride =
                Ride.builder()

                        .user(user)

                        .driver(driver)

                        .pickup(
                                request.getPickup()
                        )

                        .destination(
                                request.getDestination()
                        )

                        .pickupLatitude(
                                request.getPickupLatitude()
                        )

                        .pickupLongitude(
                                request.getPickupLongitude()
                        )

                        .destinationLatitude(
                                request.getDestinationLatitude()
                        )

                        .destinationLongitude(
                                request.getDestinationLongitude()
                        )

                        .vehicleType(
                                request.getVehicleType()
                        )

                        .distanceKm(
                                distance
                        )

                        .fare(
                                fare
                        )

                        .etaMinutes(
                                etaMinutes
                        )

                        .driverLatitude(
                                request.getPickupLatitude()
                        )

                        .driverLongitude(
                                request.getPickupLongitude()
                        )

                        .rideOtp(
                                UUID.randomUUID()
                                        .toString()
                                        .substring(0, 6)
                        )

                        .status(
                                RideStatus.PENDING
                        )

                        .bookedAt(
                                LocalDateTime.now()
                        )

                        .build();


        Ride savedRide =
                rideRepository.save(ride);


        // =================================
        // NOTIFICATION
        // =================================

        notificationService.createNotification(
                user,
                "Your ride booked successfully"
        );


        return savedRide;
    }


    // =================================
    // ESTIMATE RIDE
    // =================================

    public Ride estimateRide(
            RideBookingRequest request
    ) {


        // =================================
        // VALIDATE LOCATIONS
        // =================================

        if (
                request.getPickupLatitude() == null
                        || request.getPickupLongitude() == null
                        || request.getDestinationLatitude() == null
                        || request.getDestinationLongitude() == null
        ) {

            throw new RuntimeException(
                    "Pickup and destination coordinates are required"
            );
        }


        // =================================
        // REAL ROAD ROUTE
        // =================================

        RoutingService.RouteResult route =
                routingService.calculateRoute(
                        request.getPickupLatitude(),
                        request.getPickupLongitude(),
                        request.getDestinationLatitude(),
                        request.getDestinationLongitude()
                );


        // =================================
        // REAL DISTANCE
        // =================================

        double distance =
                route.distanceKm();


        // =================================
        // REAL ETA
        // =================================

        int etaMinutes =
                route.durationMinutes();


        // =================================
        // FARE
        // =================================

        double fare =
                fareService.rideFare(
                        request.getVehicleType(),
                        distance
                );


        // =================================
        // RETURN ESTIMATE
        // =================================

        return Ride.builder()

                .pickup(
                        request.getPickup()
                )

                .destination(
                        request.getDestination()
                )

                .pickupLatitude(
                        request.getPickupLatitude()
                )

                .pickupLongitude(
                        request.getPickupLongitude()
                )

                .destinationLatitude(
                        request.getDestinationLatitude()
                )

                .destinationLongitude(
                        request.getDestinationLongitude()
                )

                .vehicleType(
                        request.getVehicleType()
                )

                .distanceKm(
                        distance
                )

                .fare(
                        fare
                )

                .etaMinutes(
                        etaMinutes
                )

                .build();
    }


    // =================================
    // USER RIDES
    // =================================

    public List<Ride> myRides(
            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        return rideRepository
                .findByUserOrderByBookedAtDesc(
                        user
                );
    }


    // =================================
    // DRIVER RIDES
    // =================================

    public List<Ride> driverRides(
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


        return rideRepository
                .findByDriverOrderByBookedAtDesc(
                        driver
                );
    }


    // =================================
    // RATE RIDE
    // =================================

    public Ride rateRide(
            Long rideId,
            RideRatingRequest request
    ) {

        Ride ride =
                rideRepository
                        .findById(rideId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ride not found"
                                )
                        );


        ride.setRating(
                request.getRating()
        );


        ride.setReview(
                request.getReview()
        );


        return rideRepository.save(ride);
    }


    // =================================
    // COMPLETE RIDE
    // =================================

    public Ride completeRide(
            Long rideId
    ) {

        Ride ride =
                rideRepository
                        .findById(rideId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ride not found"
                                )
                        );


        ride.setStatus(
                RideStatus.COMPLETED
        );


        ride.setCompletedAt(
                LocalDateTime.now()
        );


        return rideRepository.save(ride);
    }


    // =================================
    // START RIDE
    // =================================

    public Ride startRide(
            Long rideId
    ) {

        Ride ride =
                rideRepository
                        .findById(rideId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ride not found"
                                )
                        );


        ride.setStatus(
                RideStatus.STARTED
        );


        ride.setStartedAt(
                LocalDateTime.now()
        );


        return rideRepository.save(ride);
    }


    // =================================
    // CANCEL RIDE
    // =================================

    public Ride cancelRide(
            Long rideId
    ) {

        Ride ride =
                rideRepository
                        .findById(rideId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ride not found"
                                )
                        );


        ride.setStatus(
                RideStatus.CANCELLED
        );


        ride.setCancelledAt(
                LocalDateTime.now()
        );


        return rideRepository.save(ride);
    }


    // =================================
    // UPDATE TRACKING
    // =================================

    public Ride updateTracking(
            Long rideId,
            Double latitude,
            Double longitude,
            Integer eta
    ) {

        Ride ride =
                rideRepository
                        .findById(rideId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Ride not found"
                                )
                        );


        ride.setDriverLatitude(
                latitude
        );


        ride.setDriverLongitude(
                longitude
        );


        ride.setEtaMinutes(
                eta
        );


        ride.setStatus(
                RideStatus.DRIVER_ON_THE_WAY
        );


        return rideRepository.save(ride);
    }
}