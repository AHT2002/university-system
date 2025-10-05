package com.example.university_system.repository.user;

import com.example.university_system.entity.UserEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends BaseUserRepository<UserEntity> {
}
