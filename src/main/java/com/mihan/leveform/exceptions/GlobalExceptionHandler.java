package com.mihan.leveform.exceptions;


import com.mihan.leveform.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        logger.error("Unexpected error", ex);
        return buildErrorResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        return buildErrorResponse(message, status, null);
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status, String details) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("message", message);
        error.put("status", status.value());
        if (details != null) error.put("details", details);
        return new ResponseEntity<>(error, status);
    }
}
