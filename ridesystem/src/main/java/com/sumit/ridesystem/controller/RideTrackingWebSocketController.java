package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.RideTrackingMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor

public class RideTrackingWebSocketController {

    private final SimpMessagingTemplate
            messagingTemplate;

    // =================================
    // DRIVER LIVE LOCATION
    // =================================

    @MessageMapping("/ride/tracking")
    public void updateRideTracking(

            RideTrackingMessage message
    ) {

        messagingTemplate.convertAndSend(

                "/topic/rides/" +
                        message.getRideId(),

                message
        );
    }

    // =================================
    // DRIVER ETA UPDATE
    // =================================

    @MessageMapping("/ride/eta")
    public void updateEta(

            RideTrackingMessage message
    ) {

        messagingTemplate.convertAndSend(

                "/topic/rides/eta/" +
                        message.getRideId(),

                message
        );
    }
}