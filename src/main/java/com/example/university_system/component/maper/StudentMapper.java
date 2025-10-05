package com.example.university_system.component.maper;

import com.example.university_system.dto.student.AddStudentDTO;
import com.example.university_system.dto.student.ViewStudentDTO;
import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.enums.UserRules;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class StudentMapper implements BaseMapper<StudentEntity, AddStudentDTO, ViewStudentDTO>{

    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentEntity toAddEntity(AddStudentDTO addStudentDTO) {
        StudentEntity studentEntity = mapper.map(addStudentDTO, StudentEntity.class);
        studentEntity.setBirthDay(new Date(addStudentDTO.getBirthDayTimeStamp()));
        studentEntity.setPassword(passwordEncoder.encode(addStudentDTO.getPassword()));
        studentEntity.setPhoneNumber(addStudentDTO.getPhoneNumber());

        Set<UserRules> roles = new HashSet<>();
        roles.add(UserRules.STUDENT);
        studentEntity.setUserRules(roles);

        return studentEntity;
    }

    @Override
    public ViewStudentDTO toViewDto(StudentEntity studentEntity) {
        ViewStudentDTO viewStudentDTO = mapper.map(studentEntity, ViewStudentDTO.class);

        List<Integer> coursesCode = studentEntity.getCourseGrades().stream()
                .map(StudentCourseGradeEntity::getCourse)
                .map(course -> course.getCode())
                .toList();

        viewStudentDTO.setCourseCodes(coursesCode);
        viewStudentDTO.setBirthDayTimeStamp(studentEntity.getBirthDay().getTime());
        viewStudentDTO.setGenderString(studentEntity.getGender().name());

        return viewStudentDTO;
    }

    @Override
    public List<ViewStudentDTO> toListViewDTO(List<StudentEntity> studentEntityList) {
        return studentEntityList.stream().map(this::toViewDto).toList();
    }
}
