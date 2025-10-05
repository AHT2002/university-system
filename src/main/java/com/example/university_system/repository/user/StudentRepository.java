package com.example.university_system.repository.user;


import com.example.university_system.entity.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends BaseUserRepository<StudentEntity>{
    Optional<StudentEntity> findByStdNumber(long stdNumber);
}
