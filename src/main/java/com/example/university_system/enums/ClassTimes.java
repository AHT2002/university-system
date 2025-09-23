package com.example.university_system.enums;

import lombok.Getter;

@Getter
public enum ClassTimes {
    TIME1("۸:۰۰ تا ۱۰:۰۰"),
    TIME2("۱۰:۰۰ تا ۱۲:۰۰"),
    TIME3("۱۳:۰۰ تا ۱۵:۰۰"),
    TIME4("۱۵:۰۰ تا ۱۷:۰۰"),
    TIME5("۱۷:۰۰ تا ۱۹:۰۰"),
    ;

    private String title;
    ClassTimes(String classtime) {
        this.title = classtime;
    }
}
