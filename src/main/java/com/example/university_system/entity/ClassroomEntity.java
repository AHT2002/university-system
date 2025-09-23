package com.example.university_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClassroomEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private FacultyEntity faculty;

    @Column(nullable = false)
    @Positive
    private int capacity;

    @Column(nullable = false)
    private boolean isActive = true;
}
