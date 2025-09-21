package com.example.university_system.dto.StudentCourseGrade;

import com.example.university_system.dto.base.UpdateBaseDTO;
import com.example.university_system.enums.CourseGradeStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateGradeDTO extends UpdateBaseDTO {

    @Min(value = 0, message = "نمره نمی‌تواند کمتر از ۰ باشد")
    @Max(value = 20, message = "نمره نمی‌تواند بیشتر از ۲۰ باشد")
    private Float grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseGradeStatus status;
}
