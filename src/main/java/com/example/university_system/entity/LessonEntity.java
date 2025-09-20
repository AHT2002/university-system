package com.example.university_system.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class LessonEntity extends BaseEntity{
    @Column(unique = true, nullable = false, updatable = false)
    private int lessonCode;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer units;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentEntity departmentEntity;

    @ManyToMany
    @JoinTable(
            name = "lesson_prerequisite",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_id")
    )
    private List<LessonEntity> prerequisites = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "lesson_corequisite",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "corequisite_id")
    )
    private List<LessonEntity> corequisites = new ArrayList<>();
}
