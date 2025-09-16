package com.example.university_system.entity;


import com.example.university_system.enums.AcademicLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class StudentEntity extends UserEntity {

    @Column(unique = true, nullable = false, updatable = false)
    private long stdNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcademicLevel academicLevel;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<StudentCourseGradeEntity> courseGrades = new HashSet<>();
}
