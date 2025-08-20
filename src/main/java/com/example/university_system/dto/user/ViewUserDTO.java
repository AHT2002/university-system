package com.example.university_system.dto.user;

import com.example.university_system.dto.base.ViewBaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewUserDTO extends ViewBaseDTO {
    private String name;
    private String family;
    private String nationalCode;
    private String genderString;
    private Long birthDayTimeStamp;
    private String username;
}
