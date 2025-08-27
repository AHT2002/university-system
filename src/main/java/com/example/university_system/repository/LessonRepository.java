package com.example.university_system.repository;

import com.example.university_system.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    Optional<LessonEntity> findByLessonCode(int code);
}
