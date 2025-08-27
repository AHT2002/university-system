package com.example.university_system.component.maper;

import com.example.university_system.dto.department.AddDepartmentDTO;
import com.example.university_system.dto.department.UpdateDepartmentDTO;
import com.example.university_system.dto.department.ViewDepartmentDTO;
import com.example.university_system.entity.DepartmentEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DepartmentMapper {

    private final ModelMapper mapper;

    public DepartmentEntity toEntity(AddDepartmentDTO addDepartmentDTO) {
        return mapper.map(addDepartmentDTO, DepartmentEntity.class);
    }

    public DepartmentEntity toEntity(UpdateDepartmentDTO updateDepartmentDTO) {
        return mapper.map(updateDepartmentDTO, DepartmentEntity.class);
    }

    public ViewDepartmentDTO toViewDto(DepartmentEntity departmentEntity) {
        return mapper.map(departmentEntity, ViewDepartmentDTO.class);
    }

    public List<ViewDepartmentDTO> toListViewDTO(List<DepartmentEntity> departmentEntityList) {
        return departmentEntityList.stream().map(this::toViewDto).toList();
    }
}