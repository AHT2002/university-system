package com.example.university_system.repository;

import com.example.university_system.entity.StudentCourseGradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentCourseGradeRepository extends JpaRepository<StudentCourseGradeEntity, Long> {
    Optional<StudentCourseGradeEntity> findByStudentIdAndCourseId(Long studentId, Long courseId);
    List<StudentCourseGradeEntity> findByCourseId(Long courseId);
}
