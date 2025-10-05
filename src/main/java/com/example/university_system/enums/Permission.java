package com.example.university_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    FACULTY_READ("faculty:read"),
    FACULTY_READ_ALL("faculty:readAll"),
    FACULTY_UPDATE("faculty:update"),
    FACULTY_CREATE("faculty:create"),
    FACULTY_DELETE("faculty:delete"),

    CLASSROOM_READ("classroom:read"),
    CLASSROOM_READ_ALL("classroom:readAll"),
    CLASSROOM_UPDATE("classroom:update"),
    CLASSROOM_CREATE("classroom:create"),
    CLASSROOM_DELETE("classroom:delete"),

    COURSE_READ("course:read"),
    COURSE_READ_ALL("course:readAll"),
    COURSE_UPDATE("course:update"),
    COURSE_CREATE("course:create"),
    COURSE_DELETE("course:delete"),

    DEPARTMENT_READ("department:read"),
    DEPARTMENT_READ_ALL("department:readAll"),
    DEPARTMENT_UPDATE("department:update"),
    DEPARTMENT_CREATE("department:create"),
    DEPARTMENT_DELETE("department:delete"),

    LECTURE_READ("lecture:read"),
    LECTURE_READ_ALL("lecture:readAll"),
    LECTURE_UPDATE("lecture:update"),
    LECTURE_CREATE("lecture:create"),
    LECTURE_DELETE("lecture:delete"),

    LESSON_READ("lesson:read"),
    LESSON_READ_ALL("lesson:readAll"),
    LESSON_UPDATE("lesson:update"),
    LESSON_CREATE("lesson:create"),
    LESSON_DELETE("lesson:delete"),

    PROFESSOR_READ("professor:read"),
    PROFESSOR_READ_ALL("professor:readAll"),
    PROFESSOR_UPDATE("professor:update"),
    PROFESSOR_CREATE("professor:create"),
    PROFESSOR_DELETE("professor:delete"),
    PROFESSOR_READ_ALL_COURSES("professor:readCourses"),

    STUDENT_COURSE_READ("studentCourse:read"),
    STUDENT_COURSE_READ_ALL("studentCourse:readAll"),
    STUDENT_COURSE_UPDATE("studentCourse:update"),
    STUDENT_COURSE_CREATE("studentCourse:create"),
    STUDENT_COURSE_DELETE("studentCourse:delete"),

    PROFESSOR_COURSE_READ("professorCourse:read"),
    PROFESSOR_COURSE_UPDATE("professorCourse:update"),
    PROFESSOR_COURSE_CREATE("professorCourse:create"),
    PROFESSOR_COURSE_DELETE("professorCourse:delete"),

    STUDENT_COURSE_GRADE_READ("studentCourseGrade:read"),
    STUDENT_COURSE_GRADE_READ_ALL("studentCourseGrade:readAll"),
    STUDENT_COURSE_GRADE_UPDATE("studentCourseGrade:update"),
    STUDENT_COURSE_GRADE_CREATE("studentCourseGrade:create"),
    STUDENT_COURSE_GRADE_DELETE("studentCourseGrade:delete"),

    STUDENT_READ("student:read"),
    STUDENT_READ_ALL("student:readAll"),
    STUDENT_UPDATE("student:update"),
    STUDENT_CREATE("student:create"),
    STUDENT_DELETE("student:delete"),
    STUDENT_READ_ALL_COURSES("student:readCourses"),
    ;


    @Getter
    private final String permission;
}
