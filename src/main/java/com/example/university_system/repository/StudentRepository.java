package com.example.university_system.repository;


import com.example.university_system.entity.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends UserRepository<StudentEntity>{
    Optional<StudentEntity> findByStdNumber(long stdNumber);
}
