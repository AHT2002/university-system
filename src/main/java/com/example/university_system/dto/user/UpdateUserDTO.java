package com.example.university_system.dto.user;

import com.example.university_system.dto.base.UpdateBaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO extends UpdateBaseDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String family;

    @NotBlank
    private String password;
}
