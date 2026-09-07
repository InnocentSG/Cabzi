package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.SubscriptionRequest;

import com.sumit.ridesystem.model.DriverSubscription;
import com.sumit.ridesystem.model.Subscription;

import com.sumit.ridesystem.service.SubscriptionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/subscriptions")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class SubscriptionController {

    private final SubscriptionService
            subscriptionService;

    // =================================
    // ACTIVE SUBSCRIPTIONS
    // =================================

    @GetMapping
    public ResponseEntity<List<Subscription>>
    getActiveSubscriptions() {

        return ResponseEntity.ok(

                subscriptionService
                        .getActiveSubscriptions()
        );
    }

    // =================================
    // ALL SUBSCRIPTIONS
    // =================================

    @GetMapping("/all")
    public ResponseEntity<List<Subscription>>
    allSubscriptions() {

        return ResponseEntity.ok(

                subscriptionService
                        .allSubscriptions()
        );
    }

    // =================================
    // CREATE SUBSCRIPTION
    // =================================

    @PostMapping
    public ResponseEntity<Subscription>
    createSubscription(

            @RequestBody
            SubscriptionRequest request
    ) {

        return ResponseEntity.ok(

                subscriptionService
                        .createSubscription(
                                request
                        )
        );
    }

    // =================================
    // DRIVER SUBSCRIPTIONS
    // =================================

    @GetMapping("/my")
    public ResponseEntity<List<DriverSubscription>>
    mySubscriptions(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                subscriptionService
                        .mySubscriptions(
                                authentication
                        )
        );
    }

    // =================================
    // REQUEST SUBSCRIPTION
    // =================================

    @PostMapping("/{subscriptionId}/request")
    public ResponseEntity<DriverSubscription>
    requestSubscription(

            @PathVariable Long subscriptionId,

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                subscriptionService
                        .requestSubscription(

                                subscriptionId,

                                authentication
                        )
        );
    }

    // =================================
    // APPROVE SUBSCRIPTION
    // =================================

    @PatchMapping("/{requestId}/approve")
    public ResponseEntity<DriverSubscription>
    approveSubscription(

            @PathVariable Long requestId
    ) {

        return ResponseEntity.ok(

                subscriptionService
                        .approveSubscription(
                                requestId
                        )
        );
    }
}