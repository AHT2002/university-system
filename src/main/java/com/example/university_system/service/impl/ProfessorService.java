package com.example.university_system.service.impl;

import com.example.university_system.enums.Messages;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.repository.ProfessorRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    protected void updateEntity(ProfessorEntity entity, ProfessorEntity existingEntity) {
        existingEntity.setName(entity.getName());
        existingEntity.setFamily(entity.getFamily());
        existingEntity.setGender(entity.getGender());
        existingEntity.setBirthDay(entity.getBirthDay());
        existingEntity.setPassword(entity.getPassword());
        existingEntity.setAcademicRank(entity.getAcademicRank());
    }

    @Override
    public ProfessorEntity save(ProfessorEntity entity) {
        checkUniqueFields(entity);
        return super.save(entity);
    }

    private void checkUniqueFields(ProfessorEntity entity) {
        checkUniqueField(entity.getNationalCode(), professorRepository::findByNationalCode, Messages.PROFESSOR_AVAILABLE_BY_NATIONALCODE);
        checkUniqueField(entity.getUsername(), professorRepository::findByUsername, Messages.PROFESSOR_AVAILABLE_BY_USERNAME);
        checkUniqueField(entity.getCode(), professorRepository::findByCode, Messages.PROFESSOR_AVAILABLE_BY_CODE);
    }

    public ProfessorEntity findByCode(int code) {
        return findByField(code, professorRepository::findByCode);
    }

    public List<CourseEntity> listCoursesProfessor(int code) {
        ProfessorEntity professor = findByCode(code);
        return professor.getCourses().stream().toList();
    }
}