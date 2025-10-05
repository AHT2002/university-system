package com.example.university_system.dto;

import com.example.university_system.dto.security.authentication.response.AuthenticationResponseDto;
import com.example.university_system.dto.user.ViewUserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterUserResponseDto<V extends ViewUserDTO> {
    private V user;
    private AuthenticationResponseDto authentication;
}
