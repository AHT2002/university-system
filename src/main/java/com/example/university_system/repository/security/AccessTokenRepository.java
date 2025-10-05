package com.example.university_system.repository.security;

import com.example.university_system.entity.security.AccessTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenRepository extends BaseTokenRepository<AccessTokenEntity> {
}
