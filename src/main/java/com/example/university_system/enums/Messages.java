package com.example.university_system.enums;

import lombok.Getter;

@Getter
public enum Messages {
    // Not Found Exception Messages
    // Course
    COURSE_NOT_FOUND("درس مورد نظر پیدا نشد"),
    COURSE_DOES_NOT_HAVE_PROFESSOR("درس مورد نظر درحال حاضر استاد ندارد"),
    COURSE_DOES_NOT_HAVE_ANY_STUDENT("درس مورد نظر درحال حاضر هیچ دانشجویی ندارد"),
    // Student
    STUDENT_NOT_FOUND("دانشجوی مورد نظر پیدا نشد"),
    STUDENT_DOES_NOT_HAVE_THE_COURSE("دانشجوی این درس را اخذ نکرد"),
    // Professor
    PROFESSOR_NOT_FOUND("استاد مورد نظر پیدا نشد"),
    PROFESSOR_DOES_NOT_HAVE_THIS_COURSE("این درس توسط این استاد ارائه نشده"),
    // Department
    DEPARTMENT_NOT_FOUND("دانشکده مدنظر یافت نشد"),
    // Lesson
    LESSON_NOT_FOUND("عنوان درسی مدنظر یافت نشد"),


    // Conflict Exception messages
    // Professors
    PROFESSOR_AVAILABLE_BY_NATIONALCODE("استاد با کد ملی داده شده در سیستم موجود است"),
    PROFESSOR_AVAILABLE_BY_USERNAME("استاد با نام کاربری داده شده در سیستم موجود است"),
    PROFESSOR_AVAILABLE_BY_CODE("استاد با شماره استادی داده شده در سیستم موجود است"),
    // Students
    STUDENT_AVAILABLE_BY_NATIONALCODE("دانشجو با کد ملی داده شده در سیستم موجود است"),
    STUDENT_AVAILABLE_BY_USERNAME("دانشجو با نام کاربری داده شده در سیستم موجود است"),
    STUDENT_AVAILABLE_BY_STDNUMBER("دانشجو با شماره دانشجویی داده شده در سیستم موجود است"),
    // Courses
    COURSES_AVAILABLE_BY_CODE("درس با کد داده شده در سیستم موجود است"),
    // Department
    DEPARTMENT_NAME_ALREADY_EXISTS("این دانشکده در سیستم موجود است"),
    // Lesson
    LESSON_CODE_ALREADY_EXISTS("عنوان درسی با کد داده شده در سیستم موجود است"),



    // IllegalArgumentException
    // NationalID validator
    NATIONAL_ID_SHOULD_NOT_NULL("کد ملی نمی‌تواند بدون مقدار باشد"),
    NATIONAL_ID_REGEX_NOT_FIT("کد ملی باید دقیقاً ۱۰ رقم باشد و فقط شامل ارقام باشد"),
    NATIONAL_ID_ALL_DIGITS_CANNOT_BE_SAME("کد ملی نامعتبر است، همه ارقام نمی‌توانند یکسان باشند"),
    NATIONAL_ID_NOT_VALID("کد ملی نامعتبر است، طبق الگوریتم صحت‌سنجی، صحت کد ملی معتبر نیست"),

    // Grade
    GRADE_NOT_FOUND("نمره یافت نشد"),
    DUPLICATE_GRADE("جفت واحد درسی و دانشجوی تکراری"),
    GRADE_NOT_VALID("نمره وارد شده نامعتبر است\nمقدار نمره باید بین ۰ تا ۲۰ باشد"),
    ;


    private String description;
    Messages(String description) {
        this.description = description;
    }
}
