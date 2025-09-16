package com.example.university_system.entity;


import com.example.university_system.enums.CourseGradeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class StudentCourseGradeEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @Column(nullable = true)
    @Max(20)
    @Min(0)
    private Float grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseGradeStatus status;
}
