package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.PaymentRequest;

import com.sumit.ridesystem.model.Payment;
import com.sumit.ridesystem.model.PaymentStatus;
import com.sumit.ridesystem.model.Ride;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.PaymentRepository;
import com.sumit.ridesystem.repository.RideRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class PaymentService {

    private final PaymentRepository
            paymentRepository;

    private final RideRepository
            rideRepository;

    // =================================
    // MAKE PAYMENT
    // =================================

    public Payment makePayment(

            Authentication authentication,

            PaymentRequest request
    ) {

        User user =
                (User) authentication.getPrincipal();

        Ride ride = null;

        if (

                request.getRideId()
                        != null

        ) {

            ride =

                    rideRepository
                            .findById(
                                    request.getRideId()
                            )

                            .orElseThrow(() ->

                                    new RuntimeException(
                                            "Ride not found"
                                    )
                            );
        }

        Payment payment =

                Payment.builder()

                        .user(user)

                        .ride(ride)

                        .amount(
                                request.getAmount()
                        )

                        .purpose(
                                request.getPurpose()
                        )

                        .transactionId(
                                UUID.randomUUID()
                                        .toString()
                        )

                        .status(
                                PaymentStatus.SUCCESS
                        )

                        .paidAt(
                                LocalDateTime.now()
                        )

                        .build();

        return paymentRepository.save(
                payment
        );
    }

    // =================================
    // PAYMENT HISTORY
    // =================================

    public List<Payment>
    paymentHistory(

            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        return paymentRepository
                .findByUserOrderByPaidAtDesc(
                        user
                );
    }
}