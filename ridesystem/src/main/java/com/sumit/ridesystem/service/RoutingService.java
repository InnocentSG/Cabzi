package com.sumit.ridesystem.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RoutingService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public RoutingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public RouteResult calculateRoute(
            double pickupLatitude,
            double pickupLongitude,
            double destinationLatitude,
            double destinationLongitude
    ) {

        String url = UriComponentsBuilder
                .fromHttpUrl(
                        "https://router.project-osrm.org/route/v1/driving/"
                )
                .path(
                        pickupLongitude + "," +
                                pickupLatitude + ";" +
                                destinationLongitude + "," +
                                destinationLatitude
                )
                .queryParam("overview", "false")
                .build()
                .toUriString();

        try {

            String response =
                    restTemplate.getForObject(
                            url,
                            String.class
                    );

            if (response == null) {
                throw new RuntimeException(
                        "Empty response from routing service"
                );
            }

            JsonNode root =
                    objectMapper.readTree(response);

            String code =
                    root.path("code").asText();

            if (!"Ok".equals(code)) {

                throw new RuntimeException(
                        "Route could not be calculated"
                );
            }

            JsonNode routes =
                    root.path("routes");

            if (
                    !routes.isArray()
                            || routes.isEmpty()
            ) {

                throw new RuntimeException(
                        "No route found"
                );
            }

            JsonNode route =
                    routes.get(0);

            // OSRM distance is meters
            double distanceKm =
                    route
                            .path("distance")
                            .asDouble()
                            / 1000.0;

            // OSRM duration is seconds
            int durationMinutes =
                    (int) Math.ceil(
                            route
                                    .path("duration")
                                    .asDouble()
                                    / 60.0
                    );

            return new RouteResult(
                    distanceKm,
                    Math.max(
                            1,
                            durationMinutes
                    )
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to calculate real road route: "
                            + e.getMessage(),
                    e
            );
        }
    }

    public record RouteResult(
            double distanceKm,
            int durationMinutes
    ) {
    }
}