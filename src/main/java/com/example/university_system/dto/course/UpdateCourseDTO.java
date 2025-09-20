package com.example.university_system.dto.course;


import com.example.university_system.dto.base.UpdateBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourseDTO extends UpdateBaseDTO {
    private Integer semester;
    private Integer lessonCode;
    private Integer professorCode;
}
