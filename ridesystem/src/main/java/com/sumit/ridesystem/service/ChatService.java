package com.sumit.ridesystem.service;

import com.sumit.ridesystem.dto.StaffMessageRequest;

import com.sumit.ridesystem.model.Role;
import com.sumit.ridesystem.model.StaffMessage;
import com.sumit.ridesystem.model.User;

import com.sumit.ridesystem.repository.StaffMessageRepository;
import com.sumit.ridesystem.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final UserRepository userRepository;

    private final StaffMessageRepository staffMessageRepository;

    public List<Map<String, Object>> participants(
            Authentication authentication
    ) {

        User currentUser =
                (User) authentication.getPrincipal();

        return userRepository.findAll()
                .stream()
                .filter(User::isEnabled)
                .filter(user ->
                        user.getRole() == Role.ADMIN
                                || user.getRole() == Role.STAFF
                                || user.getRole() == Role.DRIVER_VERIFIER
                )
                .filter(user ->
                        !user.getUserId()
                                .equals(currentUser.getUserId())
                )
                .sorted(
                        Comparator.comparing(
                                User::getName,
                                String.CASE_INSENSITIVE_ORDER
                        )
                )
                .map(user -> Map.<String, Object>of(
                        "userId", user.getUserId(),
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "role", user.getRole().name()
                ))
                .toList();
    }

    public List<StaffMessage> conversation(
            Authentication authentication,
            Long userId
    ) {

        User currentUser =
                (User) authentication.getPrincipal();

        User otherUser =
                findChatUser(userId);

        return staffMessageRepository.findConversation(
                currentUser,
                otherUser
        );
    }

    public StaffMessage sendMessage(
            Authentication authentication,
            StaffMessageRequest request
    ) {

        User sender =
                (User) authentication.getPrincipal();

        User receiver =
                findChatUser(
                        request.getReceiverId()
                );

        StaffMessage message =
                StaffMessage.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .message(
                                request.getMessage()
                        )
                        .build();

        return staffMessageRepository.save(message);
    }

    private User findChatUser(
            Long userId
    ) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Chat user not found"
                                )
                        );

        if (
                user.getRole() != Role.ADMIN
                        && user.getRole() != Role.STAFF
                        && user.getRole() != Role.DRIVER_VERIFIER
        ) {
            throw new RuntimeException(
                    "Only admin and staff users can use this chat"
            );
        }

        return user;
    }
}
