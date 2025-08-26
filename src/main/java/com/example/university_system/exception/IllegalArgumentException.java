package com.example.university_system.exception;

import com.example.university_system.enums.Messages;

public class IllegalArgumentException extends RuntimeException {
    public IllegalArgumentException(String message) {
        super(message);
    }
}
