package com.example.university_system.dto.lecture;

import com.example.university_system.dto.base.AddBaseDTO;
import com.example.university_system.enums.ClassTimes;
import com.example.university_system.enums.DaysOfWeek;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLectureDTO extends AddBaseDTO {

    @NotBlank
    @NotNull
    private String code;

    @NotNull
    private DaysOfWeek dayOfWeek;

    @NotNull
    private ClassTimes classTime;

    @NotBlank
    @NotNull
    private String classroomCode;

    @NotNull
    private int courseCode;
}
