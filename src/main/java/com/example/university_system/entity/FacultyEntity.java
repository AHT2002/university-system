package com.example.university_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FacultyEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
