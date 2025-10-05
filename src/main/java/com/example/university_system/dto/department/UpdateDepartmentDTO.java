package com.example.university_system.dto.department;

import com.example.university_system.dto.base.UpdateBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDepartmentDTO extends UpdateBaseDTO {
    private String name;
    private String description;
    private Integer managerCode;
}