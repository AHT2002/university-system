package com.example.university_system.dto.student;

import com.example.university_system.dto.user.UpdateUserDTO;
import com.example.university_system.enums.AcademicLevel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStudentDTO extends UpdateUserDTO {
    @Enumerated(EnumType.STRING)
    @NotBlank
    private AcademicLevel academicLevel;
}
