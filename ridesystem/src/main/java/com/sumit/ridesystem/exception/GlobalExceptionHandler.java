package com.sumit.ridesystem.exception;

import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class GlobalExceptionHandler {

    // =================================
    // RESPONSE BUILDER
    // =================================

    private ResponseEntity<Map<String, Object>>
    buildResponse(

            HttpStatus status,

            String message
    ) {

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "timestamp",
                LocalDateTime.now()
        );

        response.put(
                "status",
                status.value()
        );

        response.put(
                "success",
                false
        );

        response.put(
                "message",
                message
        );

        return ResponseEntity

                .status(status)

                .body(response);
    }

    // =================================
    // RUNTIME EXCEPTION
    // =================================

    @ExceptionHandler(RuntimeException.class)

    public ResponseEntity<Map<String, Object>>
    handleRuntimeException(

            RuntimeException ex
    ) {

        return buildResponse(

                HttpStatus.BAD_REQUEST,

                ex.getMessage()
        );
    }

    // =================================
    // BAD CREDENTIALS
    // =================================

    @ExceptionHandler(BadCredentialsException.class)

    public ResponseEntity<Map<String, Object>>
    handleBadCredentials(

            BadCredentialsException ex
    ) {

        return buildResponse(

                HttpStatus.UNAUTHORIZED,

                "Invalid email or password"
        );
    }

    // =================================
    // VALIDATION ERRORS
    // =================================

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )

    public ResponseEntity<Map<String, Object>>
    handleValidation(

            MethodArgumentNotValidException ex
    ) {

        String message =

                ex.getBindingResult()

                        .getFieldError()

                        .getDefaultMessage();

        return buildResponse(

                HttpStatus.BAD_REQUEST,

                message
        );
    }

    // =================================
    // DATABASE CONSTRAINT
    // =================================

    @ExceptionHandler(
            DataIntegrityViolationException.class
    )

    public ResponseEntity<Map<String, Object>>
    handleDatabaseException(

            DataIntegrityViolationException ex
    ) {

        return buildResponse(

                HttpStatus.BAD_REQUEST,

                "Database constraint violation"
        );
    }

    // =================================
    // ILLEGAL ARGUMENT
    // =================================

    @ExceptionHandler(
            IllegalArgumentException.class
    )

    public ResponseEntity<Map<String, Object>>
    handleIllegalArgument(

            IllegalArgumentException ex
    ) {

        return buildResponse(

                HttpStatus.BAD_REQUEST,

                ex.getMessage()
        );
    }

    // =================================
    // GENERIC EXCEPTION
    // =================================

    @ExceptionHandler(Exception.class)

    public ResponseEntity<Map<String, Object>>
    handleException(

            Exception ex
    ) {

        ex.printStackTrace();

        return buildResponse(

                HttpStatus.INTERNAL_SERVER_ERROR,

                ex.getMessage()
        );
    }
}