package com.example.university_system.dto.classroom;

import com.example.university_system.dto.base.UpdateBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateClassroomDTO extends UpdateBaseDTO {
    private Integer capacity;
    private Boolean isActive;
}
