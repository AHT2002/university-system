package com.example.university_system.dto.faculty;

import com.example.university_system.dto.base.ViewBaseDTO;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ViewFacultyDTO extends ViewBaseDTO {
    private String name;
    private int managerCode;
    private String managerName;
    private int assistantCode;
    private String assistantName;
}
