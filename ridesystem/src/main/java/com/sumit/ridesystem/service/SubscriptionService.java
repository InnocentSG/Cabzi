package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.SubscriptionRequest;

import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.DriverSubscription;
import com.sumit.ridesystem.model.Subscription;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.DriverRepository;
import com.sumit.ridesystem.repository.DriverSubscriptionRepository;
import com.sumit.ridesystem.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SubscriptionService {

    private final SubscriptionRepository
            subscriptionRepository;

    private final DriverRepository
            driverRepository;

    private final DriverSubscriptionRepository
            driverSubscriptionRepository;

    private final NotificationService
            notificationService;

    // =================================
    // ACTIVE SUBSCRIPTIONS
    // =================================

    public List<Subscription>
    getActiveSubscriptions() {

        return subscriptionRepository
                .findByActiveTrue();
    }

    // =================================
    // ALL SUBSCRIPTIONS
    // =================================

    public List<Subscription>
    allSubscriptions() {

        return subscriptionRepository
                .findAll();
    }

    // =================================
    // CREATE SUBSCRIPTION
    // =================================

    public Subscription createSubscription(
            SubscriptionRequest request
    ) {

        Subscription subscription =

                Subscription.builder()

                        .planName(
                                request.getPlanName()
                        )

                        .description(
                                request.getDescription()
                        )

                        .amount(
                                request.getAmount()
                        )

                        .validityDays(
                                request.getValidityDays()
                        )

                        .rideLimit(
                                request.getRideLimit()
                        )

                        .parcelEnabled(
                                request.getParcelEnabled()
                        )

                        .prioritySupport(
                                request.getPrioritySupport()
                        )

                        .active(true)

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .build();

        return subscriptionRepository.save(
                subscription
        );
    }

    // =================================
    // DRIVER SUBSCRIPTIONS
    // =================================

    public List<DriverSubscription>
    mySubscriptions(
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

        return driverSubscriptionRepository
                .findByDriverOrderByRequestedAtDesc(driver);
    }

    // =================================
    // REQUEST SUBSCRIPTION
    // =================================

    public DriverSubscription requestSubscription(

            Long subscriptionId,

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

        Subscription subscription =

                subscriptionRepository
                        .findById(subscriptionId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Subscription not found"
                                )
                        );

        DriverSubscription request =

                DriverSubscription.builder()

                        .driver(driver)

                        .subscription(subscription)

                        .amountPaid(
                                subscription.getAmount()
                        )

                        .status(
                                "REQUESTED"
                        )

                        .requestedAt(
                                LocalDateTime.now()
                        )

                        .build();

        DriverSubscription savedRequest =

                driverSubscriptionRepository
                        .save(request);

        notificationService.createNotification(
                user,
                "Your subscription request submitted successfully"
        );

        return savedRequest;
    }

    // =================================
    // APPROVE SUBSCRIPTION
    // =================================

    public DriverSubscription approveSubscription(
            Long requestId
    ) {

        DriverSubscription request =

                driverSubscriptionRepository
                        .findById(requestId)

                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Subscription request not found"
                                )
                        );

        request.setStatus(
                "APPROVED"
        );

        request.setApprovedAt(
                LocalDateTime.now()
        );

        request.setExpiryDate(

                LocalDateTime.now()

                        .plusDays(

                                request.getSubscription()
                                        .getValidityDays()
                        )
        );

        return driverSubscriptionRepository
                .save(request);
    }
}
