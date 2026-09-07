package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.Driver;
import com.sumit.ridesystem.model.DriverSubscription;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DriverSubscriptionRepository
        extends JpaRepository<DriverSubscription, Long> {

    List<DriverSubscription>
    findByDriverOrderByRequestedAtDesc(
            Driver driver
    );

    // =================================
    // ALL SUBSCRIPTIONS DESC
    // =================================

    List<DriverSubscription>
    findAllByOrderByRequestedAtDesc();
}
