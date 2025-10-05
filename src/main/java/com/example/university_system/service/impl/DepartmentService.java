package com.example.university_system.service.impl;

import com.example.university_system.dto.department.UpdateDepartmentDTO;
import com.example.university_system.entity.DepartmentEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.repository.DepartmentRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.university_system.component.CheckRequestsInputStringParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class DepartmentService extends BaseService<DepartmentEntity, Long, UpdateDepartmentDTO> {

    private final DepartmentRepository departmentRepository;
    private final CheckRequestsInputStringParameter stringParameterChecker;
    private final ProfessorService professorService;

    @Override
    protected DepartmentRepository getRepository() {
        return departmentRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.DEPARTMENT_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "department";
    }

    @Override
    public String getAllCacheName() {
        return "allDepartment";
    }

    @Override
    protected void updateEntity(UpdateDepartmentDTO dto, DepartmentEntity existingEntity) {
        if (stringParameterChecker.checkRequestsInputStringParameter(dto.getName())) {
            existingEntity.setName(dto.getName());
        }
        if (stringParameterChecker.checkRequestsInputStringParameter(dto.getDescription())) {
            existingEntity.setDescription(dto.getDescription());
        }
        if(dto.getManagerCode() != null) existingEntity.setManager(professorService.findByCode(dto.getManagerCode()));
    }


    @Override
    protected Map<Messages, Function<Object, Optional<DepartmentEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<DepartmentEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.DEPARTMENT_NAME_ALREADY_EXISTS, name -> departmentRepository.findByName((String) name));
        return checkers;
    }

    @Override
    protected Object getFieldValue(DepartmentEntity entity, Messages message) {
        if (message == Messages.DEPARTMENT_NAME_ALREADY_EXISTS) {
            return entity.getName();
        }
        return null;
    }

    public DepartmentEntity findByName(String name) {
        return findByField(name, departmentRepository::findByName);
    }
}