package com.example.university_system.dto.lecture;

import com.example.university_system.dto.base.ViewBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewLectureDTO extends ViewBaseDTO {
    private String code;
    private String dayOfWeek;
    private String classTime;
    private String classroomCode;
    private String facultyName;
    private int courseCode;
    private int semester;
    private int lessonCode;
    private String lessonTitle;
    private String departmentName;
    private Integer professorCode;
    private String professorName;
    private String professorFamily;
}
