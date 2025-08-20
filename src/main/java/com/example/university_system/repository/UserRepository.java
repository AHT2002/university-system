package com.example.university_system.repository;

import com.example.university_system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends UserEntity> extends JpaRepository<T, Long> {
    Optional<T> findByNationalCode(String nationalCode);
    Optional<T> findByUsername(String username);
}
