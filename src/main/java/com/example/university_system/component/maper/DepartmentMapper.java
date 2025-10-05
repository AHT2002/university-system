package com.example.university_system.component.maper;

import com.example.university_system.dto.department.AddDepartmentDTO;
import com.example.university_system.dto.department.ViewDepartmentDTO;
import com.example.university_system.entity.DepartmentEntity;
import com.example.university_system.service.impl.ProfessorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DepartmentMapper implements BaseMapper<DepartmentEntity, AddDepartmentDTO, ViewDepartmentDTO>{
    private final ModelMapper mapper;
    private final ProfessorService professorService;

    @Override
    public DepartmentEntity toAddEntity(AddDepartmentDTO addDepartmentDTO) {
        DepartmentEntity departmentEntity = mapper.map(addDepartmentDTO, DepartmentEntity.class);
        departmentEntity.setManager(professorService.findByCode(addDepartmentDTO.getManagerCode()));
        return departmentEntity;
    }

    @Override
    public ViewDepartmentDTO toViewDto(DepartmentEntity departmentEntity) {
        ViewDepartmentDTO dto = mapper.map(departmentEntity, ViewDepartmentDTO.class);
        dto.setManagerCode(departmentEntity.getManager().getCode());
        dto.setManagerName(departmentEntity.getManager().getName() + " " + departmentEntity.getManager().getFamily());
        return dto;
    }

    @Override
    public List<ViewDepartmentDTO> toListViewDTO(List<DepartmentEntity> departmentEntityList) {
        return departmentEntityList.stream().map(this::toViewDto).toList();
    }
}