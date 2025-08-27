package com.example.university_system.dto.lesson;

import com.example.university_system.dto.base.ViewBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewLessonDTO extends ViewBaseDTO {
    private int lessonCode;
    private String title;
    private int units;
    private String departmentName;
}