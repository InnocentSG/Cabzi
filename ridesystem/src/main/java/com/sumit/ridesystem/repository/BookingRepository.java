package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.Booking;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    // =================================
    // USER BOOKINGS
    // =================================

    List<Booking>
    findByUserOrderByBookedAtDesc(
            User user
    );

    // =================================
    // ALL BOOKINGS DESC
    // =================================

    List<Booking>
    findAllByOrderByBookedAtDesc();
}