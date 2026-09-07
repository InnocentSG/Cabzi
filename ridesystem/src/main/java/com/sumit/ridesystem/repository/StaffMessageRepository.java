package com.sumit.ridesystem.repository;

import com.sumit.ridesystem.model.StaffMessage;
import com.sumit.ridesystem.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StaffMessageRepository
        extends JpaRepository<StaffMessage, Long> {

    // =================================
    // RECEIVED MESSAGES
    // =================================

    List<StaffMessage>
    findByReceiverOrderByCreatedAtDesc(
            User receiver
    );

    // =================================
    // SENT MESSAGES
    // =================================

    List<StaffMessage>
    findBySenderOrderByCreatedAtDesc(
            User sender
    );

    // =================================
    // ALL MESSAGES DESC
    // =================================

    List<StaffMessage>
    findAllByOrderByCreatedAtDesc();

    @Query("""
            select message from StaffMessage message
            where (
                message.sender = :firstUser
                and message.receiver = :secondUser
            ) or (
                message.sender = :secondUser
                and message.receiver = :firstUser
            )
            order by message.createdAt asc
            """)
    List<StaffMessage>
    findConversation(
            @Param("firstUser")
            User firstUser,

            @Param("secondUser")
            User secondUser
    );
}
