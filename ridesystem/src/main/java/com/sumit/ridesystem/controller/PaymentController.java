package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.PaymentRequest;

import com.sumit.ridesystem.model.Payment;

import com.sumit.ridesystem.service.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class PaymentController {

    private final PaymentService
            paymentService;

    // =================================
    // MAKE PAYMENT
    // =================================

    @PostMapping

    public ResponseEntity<Payment>
    makePayment(

            Authentication authentication,

            @RequestBody
            PaymentRequest request
    ) {

        return ResponseEntity.ok(

                paymentService.makePayment(
                        authentication,
                        request
                )
        );
    }

    // =================================
    // PAYMENT HISTORY
    // =================================

    @GetMapping

    public ResponseEntity<List<Payment>>
    paymentHistory(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                paymentService.paymentHistory(
                        authentication
                )
        );
    }
}