package com.example.university_system.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
//@RequiredArgsConstructor
public class ApiException {
    private final HttpStatus status;
    private final String message;
    private final LocalDateTime timestamp;
    private String path;
    private Map<String, String> validationErrors;

    public ApiException(HttpStatus status, String message, LocalDateTime timestamp, String path) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

    public ApiException(HttpStatus status, String message, LocalDateTime timestamp, String path, Map<String, String> validationErrors) {
        this(status, message, timestamp, path);
        this.validationErrors = validationErrors;
    }
}
