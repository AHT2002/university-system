package com.example.university_system.dto.lesson;

import com.example.university_system.dto.base.AddBaseDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLessonDTO extends AddBaseDTO {
    @NotNull
    @Positive
    private int lessonCode;

    @NotBlank
    private String title;

    @NotNull
    @Positive
    private int units;

    @NotBlank
    private Long departmentId;
}