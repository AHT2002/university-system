package com.example.university_system.service.impl;

import com.example.university_system.component.CheckRequestsInputStringParameter;
import com.example.university_system.dto.faculty.UpdateFacultyDTO;
import com.example.university_system.entity.FacultyEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.repository.FacultyRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class FacultyService extends BaseService<FacultyEntity, Long, UpdateFacultyDTO> {
    private final FacultyRepository facultyRepository;
    private final CheckRequestsInputStringParameter checkString;

    @Override
    protected FacultyRepository getRepository() {
        return facultyRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.FACULTY_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "Faculty";
    }

    @Override
    public String getAllCacheName() {
        return "allFaculty";
    }


    @Override
    protected Map<Messages, Function<Object, Optional<FacultyEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<FacultyEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.FACULTY_AVAILABLE_BY_Name, name -> facultyRepository.findByName((String) name));
        return checkers;
    }

    @Override
    protected Object getFieldValue(FacultyEntity entity, Messages message) {
        if (message == Messages.FACULTY_AVAILABLE_BY_Name) {
            return entity.getName();
        }
        return null;
    }

    @Override
    protected void updateEntity(UpdateFacultyDTO dto, FacultyEntity existingEntity) {
        if(checkString.checkRequestsInputStringParameter(dto.getName())) existingEntity.setName(dto.getName());
    }
}
