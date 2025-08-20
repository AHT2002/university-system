package com.example.university_system.dto.course;

import com.example.university_system.dto.base.ViewBaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ViewCourseDTO extends ViewBaseDTO {
    private int code;
    private String title;
    private int units;
    private String nameProfessor;
    private List<Long> studentNumbers;
}
