package com.example.university_system.repository.security;

import com.example.university_system.entity.security.RefreshTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends BaseTokenRepository<RefreshTokenEntity> {
}
