package com.sumit.ridesystem.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor

public class FirebaseService {

    @Value("${firebase.enabled:false}")
    private boolean firebaseEnabled;

    @Value("${firebase.server-key:}")
    private String serverKey;

    @Value("${firebase.fcm-url:https://fcm.googleapis.com/fcm/send}")
    private String fcmUrl;

    // =================================
    // SEND PUSH NOTIFICATION
    // =================================

    public void sendNotification(

            String token,

            String title,

            String body
    ) {

        if (
                !firebaseEnabled
                        || serverKey == null
                        || serverKey.isBlank()
                        || token == null
                        || token.isBlank()
        ) {
            return;
        }

        try {
            String payload =
                    """
                    {
                      "to": "%s",
                      "notification": {
                        "title": "%s",
                        "body": "%s"
                      }
                    }
                    """.formatted(
                            escape(token),
                            escape(title),
                            escape(body)
                    );

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(fcmUrl))
                            .header(
                                    "Authorization",
                                    "key=" + serverKey
                            )
                            .header(
                                    "Content-Type",
                                    "application/json"
                            )
                            .POST(
                                    HttpRequest.BodyPublishers.ofString(payload)
                            )
                            .build();

            HttpClient.newHttpClient()
                    .send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );

        } catch (Exception exception) {
            throw new RuntimeException(
                    "Firebase notification failed",
                    exception
            );
        }
    }

    private String escape(
            String value
    ) {

        return value == null
                ? ""
                : value.replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}
