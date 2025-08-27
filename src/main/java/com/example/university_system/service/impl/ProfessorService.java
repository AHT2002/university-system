package com.example.university_system.service.impl;

import com.example.university_system.enums.Messages;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.repository.ProfessorRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ProfessorService extends BaseService<ProfessorEntity, Long> {

    private final ProfessorRepository professorRepository;

    @Override
    protected ProfessorRepository getRepository() {
        return professorRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.PROFESSOR_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "professor";
    }

    @Override
    public String getAllCacheName() {
        return "allProfessor";
    }


    @Override
    protected Map<Messages, Function<Object, Optional<ProfessorEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<ProfessorEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.PROFESSOR_AVAILABLE_BY_NATIONALCODE, nationalCode -> professorRepository.findByNationalCode((String) nationalCode));
        checkers.put(Messages.PROFESSOR_AVAILABLE_BY_USERNAME, username -> professorRepository.findByUsername((String) username));
        checkers.put(Messages.PROFESSOR_AVAILABLE_BY_CODE, code -> professorRepository.findByCode((int) code));
        return checkers;
    }

    @Override
    protected Object getFieldValue(ProfessorEntity entity, Messages message) {
        switch (message) {
            case PROFESSOR_AVAILABLE_BY_NATIONALCODE:
                return entity.getNationalCode();
            case PROFESSOR_AVAILABLE_BY_USERNAME:
                return entity.getUsername();
            case PROFESSOR_AVAILABLE_BY_CODE:
                return entity.getCode();
            default:
                return null;
        }
    }


    @Override
    protected void updateEntity(ProfessorEntity entity, ProfessorEntity existingEntity) {
        existingEntity.setName(entity.getName());
        existingEntity.setFamily(entity.getFamily());
        existingEntity.setGender(entity.getGender());
        existingEntity.setBirthDay(entity.getBirthDay());
        existingEntity.setPassword(entity.getPassword());
        existingEntity.setAcademicRank(entity.getAcademicRank());
    }



    public ProfessorEntity findByCode(int code) {
        return findByField(code, professorRepository::findByCode);
    }

    public List<CourseEntity> listCoursesProfessor(int code) {
        ProfessorEntity professor = findByCode(code);
        return professor.getCourses().stream().toList();
    }
}