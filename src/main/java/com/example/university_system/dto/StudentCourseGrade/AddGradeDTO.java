package com.example.university_system.dto.StudentCourseGrade;

import com.example.university_system.dto.base.AddBaseDTO;
import com.example.university_system.enums.CourseGradeStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddGradeDTO extends AddBaseDTO {
    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;

    @Min(value = 0, message = "نمره نمی‌تواند کمتر از ۰ باشد")
    @Max(value = 20, message = "نمره نمی‌تواند بیشتر از ۲۰ باشد")
    private Float grade;

    @NotNull
    private CourseGradeStatus status;
}
