package com.sumit.ridesystem.controller;

import com.sumit.ridesystem.dto.ParcelRequest;

import com.sumit.ridesystem.model.Parcel;

import com.sumit.ridesystem.service.ParcelService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcels")

@RequiredArgsConstructor

@CrossOrigin(origins = "*")

public class ParcelController {

    private final ParcelService
            parcelService;

    // =================================
    // CREATE PARCEL
    // =================================

    @PostMapping

    public ResponseEntity<Parcel>
    createParcel(

            Authentication authentication,

            @RequestBody
            ParcelRequest request
    ) {

        return ResponseEntity.ok(

                parcelService.createParcel(
                        authentication,
                        request
                )
        );
    }

    @PostMapping("/fare")

    public ResponseEntity<Parcel>
    estimateFare(

            @RequestBody
            ParcelRequest request
    ) {

        return ResponseEntity.ok(

                parcelService.estimateParcel(
                        request
                )
        );
    }

    // =================================
    // MY PARCELS
    // =================================

    @GetMapping

    public ResponseEntity<List<Parcel>>
    myParcels(

            Authentication authentication
    ) {

        return ResponseEntity.ok(

                parcelService.myParcels(
                        authentication
                )
        );
    }
}
