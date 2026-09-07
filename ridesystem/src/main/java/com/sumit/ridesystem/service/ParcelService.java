package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.ParcelRequest;

import com.sumit.ridesystem.model.Parcel;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.ParcelRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ParcelService {

    private final ParcelRepository
            parcelRepository;

    private final FareService
            fareService;

    // =================================
    // CREATE PARCEL
    // =================================

    public Parcel createParcel(

            Authentication authentication,

            ParcelRequest request
    ) {

        User user =
                (User) authentication.getPrincipal();

        double weight =
                request.getWeightKg();

        double distance =
                fareService.resolveDistance(
                        request.getDistanceKm(),
                        request.getPickupLatitude(),
                        request.getPickupLongitude(),
                        request.getDeliveryLatitude(),
                        request.getDeliveryLongitude()
                );

        double amount =
                fareService.parcelFare(
                        distance,
                        weight
                );

        Parcel parcel =

                Parcel.builder()

                        .user(user)

                        .senderName(
                                request.getSenderName()
                        )

                        .receiverName(
                                request.getReceiverName()
                        )

                        .receiverPhone(
                                request.getReceiverPhone()
                        )

                        .pickupAddress(
                                request.getPickupAddress()
                        )

                        .deliveryAddress(
                                request.getDeliveryAddress()
                        )

                        .pickupLatitude(
                                request.getPickupLatitude()
                        )

                        .pickupLongitude(
                                request.getPickupLongitude()
                        )

                        .deliveryLatitude(
                                request.getDeliveryLatitude()
                        )

                        .deliveryLongitude(
                                request.getDeliveryLongitude()
                        )

                        .distanceKm(distance)

                        .weightKg(weight)

                        .amount(amount)

                        .status("PENDING")

                        .build();

        return parcelRepository.save(
                parcel
        );
    }

    public Parcel estimateParcel(
            ParcelRequest request
    ) {

        double distance =
                fareService.resolveDistance(
                        request.getDistanceKm(),
                        request.getPickupLatitude(),
                        request.getPickupLongitude(),
                        request.getDeliveryLatitude(),
                        request.getDeliveryLongitude()
                );

        return Parcel.builder()

                .pickupAddress(
                        request.getPickupAddress()
                )

                .deliveryAddress(
                        request.getDeliveryAddress()
                )

                .pickupLatitude(
                        request.getPickupLatitude()
                )

                .pickupLongitude(
                        request.getPickupLongitude()
                )

                .deliveryLatitude(
                        request.getDeliveryLatitude()
                )

                .deliveryLongitude(
                        request.getDeliveryLongitude()
                )

                .distanceKm(distance)

                .weightKg(
                        request.getWeightKg()
                )

                .amount(
                        fareService.parcelFare(
                                distance,
                                request.getWeightKg()
                        )
                )

                .build();
    }

    // =================================
    // MY PARCELS
    // =================================

    public List<Parcel>
    myParcels(

            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        return parcelRepository
                .findByUserOrderByCreatedAtDesc(
                        user
                );
    }
}
