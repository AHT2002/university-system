package com.example.university_system.service.impl;

import com.example.university_system.entity.DepartmentEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.repository.DepartmentRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class DepartmentService extends BaseService<DepartmentEntity, Long> {

    private final DepartmentRepository departmentRepository;

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
    protected void updateEntity(DepartmentEntity entity, DepartmentEntity existingEntity) {
        existingEntity.setName(entity.getName());
        existingEntity.setDescription(entity.getDescription());
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