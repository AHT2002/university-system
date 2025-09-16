package com.example.university_system.dto.StudentCourseGrade;

import com.example.university_system.dto.base.ViewBaseDTO;
import com.example.university_system.enums.CourseGradeStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ViewGradeDTO extends ViewBaseDTO {
    private Long studentId;
    private String studentName;
    private String StudentLastName;
    private Long courseId;
    private int courseCode;
    private String lessonTitle;
    private String professorName;
    private int units;
    private Float grade;
    private CourseGradeStatus status;
}
