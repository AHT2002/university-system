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

    @Min(0)
    @Max(20)
    private Float grade;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseGradeStatus status;
}
