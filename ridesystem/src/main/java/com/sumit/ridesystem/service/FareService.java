package com.sumit.ridesystem.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

@Service
public class FareService {

    private static final double EARTH_RADIUS_KM = 6371.0;

    public double resolveDistance(
            Double requestDistance,
            Double startLatitude,
            Double startLongitude,
            Double endLatitude,
            Double endLongitude
    ) {

        if (requestDistance != null && requestDistance > 0) {
            return round(requestDistance);
        }

        if (
                startLatitude != null
                        && startLongitude != null
                        && endLatitude != null
                        && endLongitude != null
        ) {
            return round(
                    haversineDistance(
                            startLatitude,
                            startLongitude,
                            endLatitude,
                            endLongitude
                    )
            );
        }

        throw new RuntimeException(
                "Distance or both pickup and destination coordinates are required"
        );
    }

    public double rideFare(
            String vehicleType,
            double distanceKm
    ) {

        double baseFare = 25;
        double perKm = switch (normalize(vehicleType)) {
            case "BIKE", "MOTO" -> 9;
            case "AUTO", "RICKSHAW" -> 13;
            case "SEDAN", "CAB", "CAR" -> 18;
            case "SUV" -> 24;
            case "PREMIUM" -> 32;
            default -> 18;
        };

        return round(baseFare + distanceKm * perKm);
    }

    public double parcelFare(
            double distanceKm,
            double weightKg
    ) {

        double baseFare = 35;
        double distanceCharge = distanceKm * 10;
        double weightCharge = Math.max(0, weightKg - 1) * 12;

        return round(baseFare + distanceCharge + weightCharge);
    }

    public double shareRideTotalFare(
            String vehicleType,
            double distanceKm,
            int totalSeats
    ) {

        double discountMultiplier = totalSeats >= 3 ? 0.85 : 0.90;

        return round(
                rideFare(vehicleType, distanceKm) * discountMultiplier
        );
    }

    public double perSeatFare(
            double totalFare,
            int totalSeats
    ) {

        if (totalSeats <= 0) {
            throw new RuntimeException("Total seats must be greater than 0");
        }

        return round(totalFare / totalSeats);
    }

    public int etaMinutes(
            double distanceKm
    ) {

        return Math.max(
                3,
                (int) Math.ceil(distanceKm * 3)
        );
    }

    private double haversineDistance(
            double startLatitude,
            double startLongitude,
            double endLatitude,
            double endLongitude
    ) {

        double latitudeDistance =
                Math.toRadians(endLatitude - startLatitude);

        double longitudeDistance =
                Math.toRadians(endLongitude - startLongitude);

        double a =
                Math.sin(latitudeDistance / 2)
                        * Math.sin(latitudeDistance / 2)
                        + Math.cos(Math.toRadians(startLatitude))
                        * Math.cos(Math.toRadians(endLatitude))
                        * Math.sin(longitudeDistance / 2)
                        * Math.sin(longitudeDistance / 2);

        return EARTH_RADIUS_KM * 2 * Math.atan2(
                Math.sqrt(a),
                Math.sqrt(1 - a)
        );
    }

    private String normalize(
            String value
    ) {

        return value == null
                ? ""
                : value.trim().toUpperCase(Locale.ROOT);
    }

    private double round(
            double value
    ) {

        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
