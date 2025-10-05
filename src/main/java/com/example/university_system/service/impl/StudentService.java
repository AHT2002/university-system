package com.example.university_system.service.impl;

import com.example.university_system.component.maper.StudentMapper;
import com.example.university_system.dto.RegisterUserResponseDto;
import com.example.university_system.dto.security.authentication.response.AuthenticationResponseDto;
import com.example.university_system.dto.student.AddStudentDTO;
import com.example.university_system.dto.student.UpdateStudentDTO;
import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.repository.user.StudentRepository;
import com.example.university_system.service.BaseService;
import com.example.university_system.component.CheckRequestsInputStringParameter;
import com.example.university_system.service.security.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class StudentService extends BaseService<StudentEntity, Long, UpdateStudentDTO> {

    private final StudentRepository studentRepository;
    private final CheckRequestsInputStringParameter stringParameterChecker;
    private final AuthenticationService authenticationService;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

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
    protected void updateEntity(UpdateStudentDTO dto, StudentEntity existingEntity) {
        if (stringParameterChecker.checkRequestsInputStringParameter(dto.getName())) existingEntity.setName(dto.getName());
        if (stringParameterChecker.checkRequestsInputStringParameter(dto.getFamily())) existingEntity.setFamily(dto.getFamily());
        if (dto.getPassword() != null) existingEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getAcademicLevel() != null) existingEntity.setAcademicLevel(dto.getAcademicLevel());
    }


    public StudentEntity findByStdNumber(Long stdNumber) {
        return findByField(stdNumber, studentRepository::findByStdNumber);
    }

    public List<CourseEntity> listCoursesStudent(Long stdNumber) {
        StudentEntity student = findByStdNumber(stdNumber);
        return student.getCourseGrades().stream().map(StudentCourseGradeEntity::getCourse).toList();
    }

    @CacheEvict(cacheNames = {"student", "allStudent"},
            allEntries = true,
            cacheResolver = "cacheResolver")
    public RegisterUserResponseDto register(AddStudentDTO addStudentDTO) {
        AuthenticationResponseDto authenticationResponse = authenticationService.register(addStudentDTO, studentMapper, studentRepository);
        StudentEntity student = findByStdNumber(addStudentDTO.getStdNumber());
        return RegisterUserResponseDto.builder()
                .user(studentMapper.toViewDto(student))
                .authentication(authenticationResponse)
                .build();
    }
}