package com.example.university_system.entity;


import com.example.university_system.enums.AcademicRank;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ProfessorEntity extends UserEntity {
    @Column(unique = true, nullable = false, updatable = false)
    private int code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcademicRank academicRank;

    @OneToMany(mappedBy = "professorEntity", cascade = CascadeType.ALL)
    private Set<CourseEntity> courses = new HashSet<>();
}
