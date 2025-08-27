package com.example.university_system.dto.department;

import com.example.university_system.dto.base.ViewBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewDepartmentDTO extends ViewBaseDTO {
    private String name;
    private String description;
}