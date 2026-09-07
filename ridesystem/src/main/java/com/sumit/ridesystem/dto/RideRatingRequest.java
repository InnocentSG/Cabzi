package com.sumit.ridesystem.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data

public class RideRatingRequest {

    // =================================
    // RATING
    // =================================

    @NotNull(
            message = "Rating is required"
    )

    @Min(
            value = 1,
            message = "Minimum rating is 1"
    )

    @Max(
            value = 5,
            message = "Maximum rating is 5"
    )

    private Integer rating;

    // =================================
    // REVIEW
    // =================================

    private String review;
}