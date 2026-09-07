package com.sumit.ridesystem.service;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;

import java.util.UUID;

@Service

public class FileUploadService {

    private static final String
            UPLOAD_DIR = "uploads";

    // =================================
    // UPLOAD FILE
    // =================================

    public String uploadFile(
            MultipartFile file
    ) {

        try {

            if (

                    file == null
                            ||
                            file.isEmpty()

            ) {

                throw new RuntimeException(
                        "File is empty"
                );
            }

            Path uploadPath =
                    Paths.get(UPLOAD_DIR);

            if (

                    !Files.exists(uploadPath)

            ) {

                Files.createDirectories(
                        uploadPath
                );
            }

            String fileName =

                    UUID.randomUUID()
                            + "_"
                            + file.getOriginalFilename();

            Path filePath =

                    uploadPath.resolve(
                            fileName
                    );

            Files.copy(

                    file.getInputStream(),

                    filePath,

                    StandardCopyOption.REPLACE_EXISTING
            );

            return "/uploads/" + fileName;

        } catch (IOException e) {

            throw new RuntimeException(
                    "File upload failed"
            );
        }
    }
}