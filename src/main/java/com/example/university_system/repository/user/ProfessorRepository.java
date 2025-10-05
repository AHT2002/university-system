package com.example.university_system.repository.user;

import com.example.university_system.entity.ProfessorEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends BaseUserRepository<ProfessorEntity>{
    Optional<ProfessorEntity> findByCode(int code);
}
