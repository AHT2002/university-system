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
public class DepartmentMapper implements BaseMapper<DepartmentEntity, AddDepartmentDTO, UpdateDepartmentDTO, ViewDepartmentDTO>{
    private final ModelMapper mapper;

    @Override
    public DepartmentEntity toAddEntity(AddDepartmentDTO addDepartmentDTO) {
        return mapper.map(addDepartmentDTO, DepartmentEntity.class);
    }

    @Override
    public ViewDepartmentDTO toViewDto(DepartmentEntity departmentEntity) {
        return mapper.map(departmentEntity, ViewDepartmentDTO.class);
    }

    @Override
    public List<ViewDepartmentDTO> toListViewDTO(List<DepartmentEntity> departmentEntityList) {
        return departmentEntityList.stream().map(this::toViewDto).toList();
    }
}