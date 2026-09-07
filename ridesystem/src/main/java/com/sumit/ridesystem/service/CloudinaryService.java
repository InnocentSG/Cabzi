package com.sumit.ridesystem.service;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service

public class CloudinaryService {

    // =================================
    // MOCK CLOUDINARY UPLOAD
    // =================================

    public Map<String, String> uploadFile(
            MultipartFile file
    ) {

        if (

                file == null
                        ||
                        file.isEmpty()

        ) {

            throw new RuntimeException(
                    "File is empty"
            );
        }

        Map<String, String> response =
                new HashMap<>();

        String imageUrl =

                "https://res.cloudinary.com/cabzi/"

                        + UUID.randomUUID()

                        + "_"

                        + file.getOriginalFilename();

        response.put(
                "url",
                imageUrl
        );

        response.put(
                "publicId",
                UUID.randomUUID().toString()
        );

        return response;
    }
}