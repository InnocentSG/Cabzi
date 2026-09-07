package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.StaffLog;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StaffLogRepository
        extends JpaRepository<StaffLog, Long> {

    // =================================
    // STAFF LOGS
    // =================================

    List<StaffLog>
    findByStaffOrderByCreatedAtDesc(
            User staff
    );

    // =================================
    // ALL LOGS DESC
    // =================================

    List<StaffLog>
    findAllByOrderByCreatedAtDesc();
}