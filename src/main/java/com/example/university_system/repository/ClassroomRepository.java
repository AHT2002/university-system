package com.example.university_system.repository;

import com.example.university_system.entity.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
    Optional<ClassroomEntity> findByCode(String classroomName);
}
