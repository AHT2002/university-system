package com.example.university_system.dto.faculty;

import com.example.university_system.dto.base.UpdateBaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFacultyDTO extends UpdateBaseDTO {
    @NotBlank
    private String name;
}
