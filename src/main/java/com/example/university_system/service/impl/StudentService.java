package com.example.university_system.service.impl;

import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.repository.StudentRepository;
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
public class StudentService extends BaseService<StudentEntity, Long> {

    private final StudentRepository studentRepository;

    @Override
    protected StudentRepository getRepository() {
        return studentRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.STUDENT_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "student";
    }

    @Override
    public String getAllCacheName() {
        return "allStudent";
    }


    @Override
    protected Map<Messages, Function<Object, Optional<StudentEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<StudentEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.STUDENT_AVAILABLE_BY_NATIONALCODE, nationalCode -> studentRepository.findByNationalCode((String) nationalCode));
        checkers.put(Messages.STUDENT_AVAILABLE_BY_USERNAME, username -> studentRepository.findByUsername((String) username));
        checkers.put(Messages.STUDENT_AVAILABLE_BY_STDNUMBER, stdNumber -> studentRepository.findByStdNumber((Long) stdNumber));
        return checkers;
    }

    @Override
    protected Object getFieldValue(StudentEntity entity, Messages message) {
        switch (message) {
            case STUDENT_AVAILABLE_BY_NATIONALCODE:
                return entity.getNationalCode();
            case STUDENT_AVAILABLE_BY_USERNAME:
                return entity.getUsername();
            case STUDENT_AVAILABLE_BY_STDNUMBER:
                return entity.getStdNumber();
            default:
                return null;
        }
    }


    @Override
    protected void updateEntity(StudentEntity entity, StudentEntity existingEntity) {
        existingEntity.setName(entity.getName());
        existingEntity.setFamily(entity.getFamily());
        existingEntity.setGender(entity.getGender());
        existingEntity.setBirthDay(entity.getBirthDay());
        existingEntity.setPassword(entity.getPassword());
        existingEntity.setAcademicLevel(entity.getAcademicLevel());
    }

    public StudentEntity findByStdNumber(Long stdNumber) {
        return findByField(stdNumber, studentRepository::findByStdNumber);
    }

    public List<CourseEntity> listCoursesStudent(Long stdNumber) {
        StudentEntity student = findByStdNumber(stdNumber);
        return student.getCourseGrades().stream().map(StudentCourseGradeEntity::getCourse).toList();
    }
}