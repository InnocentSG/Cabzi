package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.ShareRideRequest;

import com.sumit.ridesystem.model.ShareRide;
import com.sumit.ridesystem.model.ShareRideJoin;
import com.sumit.ridesystem.model.ShareRideStatus;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.ShareRideJoinRepository;
import com.sumit.ridesystem.repository.ShareRideRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ShareRideService {

    private final ShareRideRepository
            shareRideRepository;

    private final ShareRideJoinRepository
            shareRideJoinRepository;

    private final NotificationService
            notificationService;

    private final FareService
            fareService;

    // =================================
    // OPEN SHARE RIDES
    // =================================

    public List<ShareRide>
    getOpenRides() {

        return shareRideRepository
                .findByStatus(
                        ShareRideStatus.OPEN
                );
    }

    // =================================
    // CREATE SHARE RIDE
    // =================================

    public ShareRide createRide(

            Authentication authentication,

            ShareRideRequest request
    ) {

        User user =
                (User) authentication.getPrincipal();

        int seats =

                request.getTotalSeats() != null
                        ? request.getTotalSeats()
                        : 2;

        double distance =
                fareService.resolveDistance(
                        request.getDistanceKm(),
                        request.getPickupLatitude(),
                        request.getPickupLongitude(),
                        request.getDestinationLatitude(),
                        request.getDestinationLongitude()
                );

        double fare =
                request.getTotalFare() != null
                        ? request.getTotalFare()
                        : fareService.shareRideTotalFare(
                                request.getVehicleType(),
                                distance,
                                seats
                        );

        ShareRide shareRide =

                ShareRide.builder()

                        .creator(user)

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

                        .distanceKm(distance)

                        .vehicleType(
                                request.getVehicleType()
                        )

                        .totalSeats(seats)

                        .availableSeats(seats)

                        .totalFare(fare)

                        .perSeatFare(
                                fareService.perSeatFare(
                                        fare,
                                        seats
                                )
                        )

                        .notes(
                                request.getNotes()
                        )

                        .rideTime(
                                LocalDateTime.now()
                                        .plusMinutes(20)
                        )

                        .status(
                                ShareRideStatus.OPEN
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        ShareRide savedRide =
                shareRideRepository.save(
                        shareRide
                );

        notificationService.createNotification(
                user,
                "Your share ride created successfully"
        );

        return savedRide;
    }

    public ShareRide estimateRide(
            ShareRideRequest request
    ) {

        int seats =
                request.getTotalSeats();

        double distance =
                fareService.resolveDistance(
                        request.getDistanceKm(),
                        request.getPickupLatitude(),
                        request.getPickupLongitude(),
                        request.getDestinationLatitude(),
                        request.getDestinationLongitude()
                );

        double fare =
                request.getTotalFare() != null
                        ? request.getTotalFare()
                        : fareService.shareRideTotalFare(
                                request.getVehicleType(),
                                distance,
                                seats
                        );

        return ShareRide.builder()

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

                .distanceKm(distance)

                .vehicleType(
                        request.getVehicleType()
                )

                .totalSeats(seats)

                .availableSeats(seats)

                .totalFare(fare)

                .perSeatFare(
                        fareService.perSeatFare(
                                fare,
                                seats
                        )
                )

                .rideTime(
                        LocalDateTime.now()
                                .plusMinutes(20)
                )

                .build();
    }

    // =================================
    // JOIN SHARE RIDE
    // =================================

    public ShareRide joinRide(

            Long rideId,

            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        ShareRide shareRide =

                shareRideRepository
                        .findById(rideId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Share ride not found"
                                )
                        );

        if (

                shareRideJoinRepository
                        .existsByShareRideAndUser(
                                shareRide,
                                user
                        )

        ) {

            throw new RuntimeException(
                    "You already joined this ride"
            );
        }

        if (

                shareRide.getAvailableSeats()
                        <= 0

        ) {

            throw new RuntimeException(
                    "Ride already full"
            );
        }

        ShareRideJoin join =

                ShareRideJoin.builder()

                        .shareRide(
                                shareRide
                        )

                        .user(user)

                        .seatsBooked(1)

                        .joinedAt(
                                LocalDateTime.now()
                        )

                        .build();

        shareRideJoinRepository.save(
                join
        );

        shareRide.setAvailableSeats(

                shareRide.getAvailableSeats()
                        - 1
        );

        if (

                shareRide.getAvailableSeats()
                        == 0

        ) {

            shareRide.setStatus(
                    ShareRideStatus.FULL
            );
        }

        notificationService.createNotification(
                user,
                "You joined the share ride successfully"
        );

        return shareRideRepository.save(
                shareRide
        );
    }

    // =================================
    // START PICKUP
    // =================================

    public ShareRide startPickup(
            Long rideId
    ) {

        ShareRide shareRide =

                shareRideRepository
                        .findById(rideId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Share ride not found"
                                )
                        );

        shareRide.setStatus(
                ShareRideStatus.STARTED
        );

        return shareRideRepository.save(
                shareRide
        );
    }

    // =================================
    // COMPLETE RIDE
    // =================================

    public ShareRide completeRide(
            Long rideId
    ) {

        ShareRide shareRide =

                shareRideRepository
                        .findById(rideId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Share ride not found"
                                )
                        );

        shareRide.setStatus(
                ShareRideStatus.COMPLETED
        );

        return shareRideRepository.save(
                shareRide
        );
    }

    // =================================
    // CANCEL SHARE RIDE
    // =================================

    public ShareRide cancelRide(
            Long rideId
    ) {

        ShareRide shareRide =

                shareRideRepository
                        .findById(rideId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Share ride not found"
                                )
                        );

        shareRide.setStatus(
                ShareRideStatus.CANCELLED
        );

        return shareRideRepository.save(
                shareRide
        );
    }
}
