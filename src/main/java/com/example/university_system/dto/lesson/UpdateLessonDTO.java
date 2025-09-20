package com.example.university_system.dto.lesson;

import com.example.university_system.dto.base.UpdateBaseDTO;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class UpdateLessonDTO extends UpdateBaseDTO {
    private String title;
    @Positive
    private Integer units;
    private Long departmentId;
    private List<Long> preRequisiteLessonsId;
    private List<Long> coRequisiteLessonsId;
}