package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.StaffMessageRequest;

import com.sumit.ridesystem.model.StaffLog;
import com.sumit.ridesystem.model.StaffMessage;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.StaffLogRepository;
import com.sumit.ridesystem.repository.StaffMessageRepository;
import com.sumit.ridesystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class StaffService {

    private final StaffLogRepository
            staffLogRepository;

    private final StaffMessageRepository
            staffMessageRepository;

    private final UserRepository
            userRepository;

    // =================================
    // STAFF LOGS
    // =================================

    public List<StaffLog>
    myLogs(

            Authentication authentication
    ) {

        User staff =
                (User) authentication.getPrincipal();

        return staffLogRepository
                .findByStaffOrderByCreatedAtDesc(
                        staff
                );
    }

    // =================================
    // ALL LOGS
    // =================================

    public List<StaffLog>
    logs() {

        return staffLogRepository.findAll();
    }

    // =================================
    // STAFF MESSAGES
    // =================================

    public List<StaffMessage>
    myMessages(

            Authentication authentication
    ) {

        User user =
                (User) authentication.getPrincipal();

        return staffMessageRepository
                .findByReceiverOrderByCreatedAtDesc(
                        user
                );
    }

    // =================================
    // ALL MESSAGES
    // =================================

    public List<StaffMessage>
    messages() {

        return staffMessageRepository.findAll();
    }

    // =================================
    // SEND MESSAGE
    // =================================

    public StaffMessage sendMessage(

            Authentication authentication,

            StaffMessageRequest request
    ) {

        User sender =
                (User) authentication.getPrincipal();

        User receiver =
                userRepository.findById(
                                request.getReceiverId()
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Receiver not found"
                                )
                        );

        StaffMessage message =
                StaffMessage.builder()

                        .sender(sender)

                        .receiver(receiver)

                        .message(
                                request.getMessage()
                        )

                        .build();

        return staffMessageRepository
                .save(message);
    }

    // =================================
    // CREATE STAFF LOG
    // =================================

    public StaffLog createLog(

            User staff,

            String action,

            String details
    ) {

        StaffLog log =
                StaffLog.builder()

                        .staff(staff)

                        .action(action)

                        .details(details)

                        .build();

        return staffLogRepository
                .save(log);
    }
}
