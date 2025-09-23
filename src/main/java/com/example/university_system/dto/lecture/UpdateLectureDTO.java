package com.example.university_system.dto.lecture;

import com.example.university_system.dto.base.UpdateBaseDTO;
import com.example.university_system.enums.ClassTimes;
import com.example.university_system.enums.DaysOfWeek;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLectureDTO extends UpdateBaseDTO {

    @Enumerated(EnumType.STRING)
    private DaysOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    private ClassTimes classTime;

    private String classroomCode;
}
