package com.example.university_system.exception;

import com.example.university_system.enums.Messages;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
