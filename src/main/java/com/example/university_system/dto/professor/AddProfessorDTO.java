package com.example.university_system.dto.professor;

import com.example.university_system.dto.user.AddUserDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProfessorDTO extends AddUserDTO {
    @Positive
    private int code;

    @NotBlank
    private String academicRank;
}
