package com.example.university_system.dto.base;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateBaseDTO{

    @NotNull
    private Long id;
}
