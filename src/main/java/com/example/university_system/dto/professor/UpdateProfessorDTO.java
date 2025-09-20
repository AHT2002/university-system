package com.example.university_system.dto.professor;

import com.example.university_system.dto.user.UpdateUserDTO;
import com.example.university_system.enums.AcademicRank;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfessorDTO extends UpdateUserDTO {
    @Enumerated(EnumType.STRING)
    private AcademicRank academicRank;
}
