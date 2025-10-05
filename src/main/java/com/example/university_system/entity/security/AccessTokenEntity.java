package com.example.university_system.entity.security;

import com.example.university_system.entity.BaseEntity;
import com.example.university_system.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccessTokenEntity extends BaseEntity {
    @Column(unique = true)
    public String token;

    @Column(nullable = false)
    public boolean revoked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity user;

    @Column(nullable = false)
    private Instant issuedAt;
}
