package com.example.university_system.dto.course;

import com.example.university_system.dto.base.AddBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCourseDTO extends AddBaseDTO {

    @NotNull
    private int code;

    @NotNull
    private int semester;

    @NotNull
    private int lessonCode;
}
