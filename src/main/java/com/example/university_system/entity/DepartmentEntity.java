package com.example.university_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class DepartmentEntity extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String name;

    @Column()
    private String description;
}
