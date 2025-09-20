package com.example.university_system.component;

import org.springframework.stereotype.Component;

@Component
public class CheckRequestsInputStringParameter {
    public boolean checkRequestsInputStringParameter(String s) {
        return s != null && !s.isEmpty() && !s.equalsIgnoreCase("string");
    }
}
