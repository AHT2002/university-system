package com.example.university_system.dto.course;

import com.example.university_system.dto.base.AddBaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCourseDTO extends AddBaseDTO {

    @Positive
    private int code;

    @NotBlank
    private String title;

    @Positive
    private int units;
}
