package com.example.university_system.component.maper;

import com.example.university_system.dto.user.AddUserDTO;
import com.example.university_system.dto.user.ViewUserDTO;
import com.example.university_system.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserMapper implements BaseMapper<UserEntity, AddUserDTO, ViewUserDTO> {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity toAddEntity(AddUserDTO addUserDto) {
        return null;
    }

    @Override
    public ViewUserDTO toViewDto(UserEntity userEntity) {
        return null;
    }

    @Override
    public List<ViewUserDTO> toListViewDTO(List<UserEntity> userEntityList) {
        return userEntityList.stream().map(this::toViewDto).toList();
    }
}
