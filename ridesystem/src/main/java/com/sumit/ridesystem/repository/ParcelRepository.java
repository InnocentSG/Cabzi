package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.Parcel;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ParcelRepository
        extends JpaRepository<Parcel, Long> {

    // =================================
    // USER PARCELS
    // =================================

    List<Parcel>
    findByUserOrderByCreatedAtDesc(
            User user
    );

    // =================================
    // STATUS PARCELS
    // =================================

    List<Parcel>
    findByStatus(
            String status
    );

    // =================================
    // ALL PARCELS DESC
    // =================================

    List<Parcel>
    findAllByOrderByCreatedAtDesc();
}