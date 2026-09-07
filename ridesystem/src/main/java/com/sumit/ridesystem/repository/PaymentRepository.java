package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.Payment;
import com.sumit.ridesystem.model.PaymentStatus;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    // =================================
    // USER PAYMENTS
    // =================================

    List<Payment>
    findByUserOrderByPaidAtDesc(
            User user
    );

    // =================================
    // STATUS PAYMENTS
    // =================================

    List<Payment>
    findByStatus(
            PaymentStatus status
    );

    // =================================
    // USER STATUS PAYMENTS
    // =================================

    List<Payment>
    findByUserAndStatus(
            User user,
            PaymentStatus status
    );

    // =================================
    // ALL PAYMENTS DESC
    // =================================

    List<Payment>
    findAllByOrderByPaidAtDesc();
}