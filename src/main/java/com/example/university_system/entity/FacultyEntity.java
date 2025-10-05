package com.example.university_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JoinColumn(name = "manager_code", nullable = false, unique = true)
    private ProfessorEntity manager;

    @OneToOne
    @JoinColumn(name = "assistant_code", unique = true, nullable = false)
    private ProfessorEntity assistant;
}
