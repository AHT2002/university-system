package com.example.university_system.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.university_system.enums.Permission.*;

@RequiredArgsConstructor
public enum UserRules {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    FACULTY_READ,
                    FACULTY_UPDATE,
                    FACULTY_CREATE,
                    FACULTY_DELETE,

                    CLASSROOM_READ,
                    CLASSROOM_UPDATE,
                    CLASSROOM_CREATE,
                    CLASSROOM_DELETE,

                    COURSE_READ,
                    COURSE_UPDATE,
                    COURSE_CREATE,
                    COURSE_DELETE,

                    DEPARTMENT_READ,
                    DEPARTMENT_UPDATE,
                    DEPARTMENT_CREATE,
                    DEPARTMENT_DELETE,

                    LECTURE_READ,
                    LECTURE_UPDATE,
                    LECTURE_CREATE,
                    LECTURE_DELETE,

                    LESSON_READ,
                    LESSON_UPDATE,
                    LESSON_CREATE,
                    LESSON_DELETE,

                    PROFESSOR_READ,
                    PROFESSOR_UPDATE,
                    PROFESSOR_CREATE,
                    PROFESSOR_DELETE,

                    STUDENT_COURSE_READ,
                    STUDENT_COURSE_UPDATE,
                    STUDENT_COURSE_CREATE,
                    STUDENT_COURSE_DELETE,

                    STUDENT_COURSE_GRADE_READ,
                    STUDENT_COURSE_GRADE_UPDATE,
                    STUDENT_COURSE_GRADE_CREATE,
                    STUDENT_COURSE_GRADE_DELETE,

                    STUDENT_READ,
                    STUDENT_UPDATE,
                    STUDENT_CREATE,
                    STUDENT_DELETE
            )
    ),
    PROFESSOR(
            Set.of(
                    STUDENT_READ,
                    STUDENT_COURSE_GRADE_READ,
                    STUDENT_COURSE_GRADE_UPDATE,
                    STUDENT_COURSE_GRADE_CREATE,
                    STUDENT_COURSE_GRADE_DELETE,
                    CLASSROOM_READ,
                    COURSE_READ,
                    DEPARTMENT_READ,
                    LECTURE_READ,
                    PROFESSOR_READ,
                    PROFESSOR_UPDATE
            )
    ),
    STUDENT(
            Set.of(
                    CLASSROOM_READ,
                    FACULTY_READ,
                    COURSE_READ,
                    LECTURE_READ,
                    LESSON_READ,
                    PROFESSOR_READ,
                    STUDENT_COURSE_READ,
                    STUDENT_COURSE_UPDATE,
                    STUDENT_COURSE_CREATE,
                    STUDENT_COURSE_DELETE,
                    STUDENT_COURSE_GRADE_READ,
                    STUDENT_READ,
                    STUDENT_UPDATE
                    )
    ),
    UNIVERSITY_PRESIDENT(
            Set.of(
                    CLASSROOM_READ,
                    FACULTY_READ,
                    COURSE_READ,
                    LECTURE_READ,
                    LESSON_READ,
                    PROFESSOR_READ,
                    STUDENT_COURSE_READ,
                    STUDENT_COURSE_GRADE_READ,
                    STUDENT_READ
            )
    ),
    FACULTY_MANAGER(
            Set.of(

            )
    ),
    FACULTY_ASSISTANT(
            Set.of(

            )
    ),
    DEPARTMENT_MANAGER(
            Set.of(
                    CLASSROOM_READ,
                    FACULTY_READ,
                    COURSE_READ,
                    LECTURE_READ,
                    LESSON_READ,
                    PROFESSOR_READ,
                    STUDENT_COURSE_READ,
                    STUDENT_COURSE_GRADE_READ,
                    STUDENT_READ,

                    LESSON_UPDATE,
                    LESSON_CREATE,
                    LESSON_DELETE,

                    COURSE_UPDATE,
                    COURSE_CREATE,
                    COURSE_DELETE
            )
    ),
    ;


    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
