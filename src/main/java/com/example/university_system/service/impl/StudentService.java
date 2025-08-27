package com.example.university_system.service.impl;

import com.example.university_system.enums.Messages;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.repository.StudentRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    protected void updateEntity(StudentEntity entity, StudentEntity existingEntity) {
        existingEntity.setName(entity.getName());
        existingEntity.setFamily(entity.getFamily());
        existingEntity.setGender(entity.getGender());
        existingEntity.setBirthDay(entity.getBirthDay());
        existingEntity.setPassword(entity.getPassword());
        existingEntity.setAcademicLevel(entity.getAcademicLevel());
    }

    @Override
    public StudentEntity save(StudentEntity entity) {
        checkUniqueFields(entity);
        return super.save(entity);
    }

    private void checkUniqueFields(StudentEntity entity) {
        checkUniqueField(entity.getNationalCode(), studentRepository::findByNationalCode, Messages.STUDENT_AVAILABLE_BY_NATIONALCODE);
        checkUniqueField(entity.getUsername(), studentRepository::findByUsername, Messages.STUDENT_AVAILABLE_BY_USERNAME);
        checkUniqueField(entity.getStdNumber(), studentRepository::findByStdNumber, Messages.STUDENT_AVAILABLE_BY_STDNUMBER);
    }

    public StudentEntity findByStdNumber(Long stdNumber) {
        return findByField(stdNumber, studentRepository::findByStdNumber);
    }

    public List<CourseEntity> listCoursesStudent(Long stdNumber) {
        StudentEntity student = findByStdNumber(stdNumber);
        return student.getCourses().stream().toList();
    }
}