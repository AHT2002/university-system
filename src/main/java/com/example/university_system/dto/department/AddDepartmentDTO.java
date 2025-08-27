package com.example.university_system.dto.department;

import com.example.university_system.dto.base.AddBaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDepartmentDTO extends AddBaseDTO {
    @NotBlank
    private String name;
    private String description;
}