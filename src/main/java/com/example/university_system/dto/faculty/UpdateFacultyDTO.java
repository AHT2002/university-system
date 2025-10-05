package com.example.university_system.dto.faculty;

import com.example.university_system.dto.base.UpdateBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFacultyDTO extends UpdateBaseDTO {
    private String name;
    private Integer manegerCode;
    private Integer assistantCode;
}
