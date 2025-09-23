package com.example.university_system.enums;

import lombok.Getter;

@Getter
public enum DaysOfWeek {
    SATURDAY("شنبه"),
    SUNDAY("یکشنبه"),
    MONDAY("دوشنبه"),
    TUESDAY("سه‌شنبه"),
    WEDNESDAY("چهارشنبه"),
    THURSDAY("پنجشنبه"),
    FRIDAY("جمعه"),
    ;

    private String title;
    DaysOfWeek(String title) {
        this.title = title;
    }
}
