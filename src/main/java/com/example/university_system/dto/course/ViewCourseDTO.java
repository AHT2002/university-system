package com.example.university_system.dto.course;

import com.example.university_system.dto.base.ViewBaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ViewCourseDTO extends ViewBaseDTO {
    private int code;
    private int semester;
    private String lessonTitle;
    private int lessonUnits;
    private String departmentName;
    private String professorName;
    List<Long> studentNumbers;
}
