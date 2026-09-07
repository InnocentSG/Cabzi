package com.sumit.ridesystem.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(

        name = "share_ride_joins",

        uniqueConstraints = {

                @UniqueConstraint(
                        columnNames = {
                                "share_ride_id",
                                "user_id"
                        }
                )
        }
)

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ShareRideJoin {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long joinId;

    // =================================
    // SHARE RIDE
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "share_ride_id"
    )

    private ShareRide shareRide;

    // =================================
    // USER
    // =================================

    @ManyToOne

    @JoinColumn(
            name = "user_id"
    )

    private User user;

    // =================================
    // SEAT COUNT
    // =================================

    @Builder.Default

    private Integer seatsBooked = 1;

    // =================================
    // TIMESTAMP
    // =================================

    @Builder.Default

    private LocalDateTime joinedAt =
            LocalDateTime.now();
}