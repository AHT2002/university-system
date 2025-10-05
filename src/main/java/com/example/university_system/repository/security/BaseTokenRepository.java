package com.example.university_system.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseTokenRepository<T> extends JpaRepository<T, Long> {
    @Query("select t from #{#entityName} t where t.user.id = :userId and t.revoked = false")
    List<T> findAllValidTokenByUser(Long userId);

    Optional<T> findByToken(String token);
}
