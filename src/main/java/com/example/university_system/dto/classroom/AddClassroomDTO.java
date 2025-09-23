package com.example.university_system.dto.classroom;

import com.example.university_system.dto.base.AddBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddClassroomDTO extends AddBaseDTO {
    @NotNull
    @NotBlank
    private String code;
    @NotNull
    private long facultyId;
    @NotNull
    private int capacity;
}
