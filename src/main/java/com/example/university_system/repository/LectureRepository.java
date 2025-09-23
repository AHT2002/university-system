package com.example.university_system.repository;

import com.example.university_system.entity.LectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<LectureEntity, Long> {
    List<LectureEntity> findByCourseId(Long courseId);
    Optional<LectureEntity> findByCode(String code);
}
