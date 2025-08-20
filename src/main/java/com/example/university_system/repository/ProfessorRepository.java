package com.example.university_system.repository;

import com.example.university_system.entity.ProfessorEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends UserRepository<ProfessorEntity>{
    Optional<ProfessorEntity> findByCode(int code);
}
