package com.example.university_system.dto.faculty;

import com.example.university_system.dto.base.AddBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFacultyDTO extends AddBaseDTO {
    @NotBlank
    private String name;

    @NotNull
    private int managerCode;

    @NotNull
    private int assistantCode;
}
