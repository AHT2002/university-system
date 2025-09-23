package com.example.university_system.service.impl;

import com.example.university_system.dto.classroom.UpdateClassroomDTO;
import com.example.university_system.entity.ClassroomEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.repository.ClassroomRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ClassroomService extends BaseService<ClassroomEntity, Long, UpdateClassroomDTO> {
    private final ClassroomRepository classroomRepository;

    @Override
    protected ClassroomRepository getRepository() {
        return classroomRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.CLASSROOM_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "classroom";
    }

    @Override
    public String getAllCacheName() {
        return "allClassroom";
    }


    @Override
    protected Map<Messages, Function<Object, Optional<ClassroomEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<ClassroomEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.CLASSROOM_AVAILABLE_BY_CODE, code -> classroomRepository.findByCode((String) code));
        return checkers;
    }

    @Override
    protected Object getFieldValue(ClassroomEntity entity, Messages message) {
        if (message == Messages.CLASSROOM_AVAILABLE_BY_CODE) {
            return entity.getCode();
        }
        return null;
    }

    @Override
    protected void updateEntity(UpdateClassroomDTO dto, ClassroomEntity existingEntity) {
        if (dto.getCapacity() != null) existingEntity.setCapacity(dto.getCapacity());
        if (dto.getIsActive() != null) existingEntity.setActive(dto.getIsActive());
    }

    public ClassroomEntity findByCode(String code) {
        return findByField(code, classroomRepository::findByCode);
    }
}
