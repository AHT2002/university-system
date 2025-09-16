package com.example.university_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class CourseEntity extends BaseEntity {

    @Column(unique = true, nullable = false)
    private int code;

    @Column(nullable = false)
    private int semester;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lessonEntity ;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private ProfessorEntity professorEntity;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<StudentCourseGradeEntity> studentGrades = new HashSet<>();
}
