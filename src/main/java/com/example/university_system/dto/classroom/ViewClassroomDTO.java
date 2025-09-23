package com.example.university_system.dto.classroom;

import com.example.university_system.dto.base.ViewBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewClassroomDTO extends ViewBaseDTO {
    private String code;
    private long facultyId;
    private String facultyName;
    private int capacity;
    private boolean isActive;
}
