package com.example.university_system.component.maper;

import com.example.university_system.dto.student.AddStudentDTO;
import com.example.university_system.dto.student.ViewStudentDTO;
import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.enums.Gender;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class StudentMapper implements BaseMapper<StudentEntity, AddStudentDTO, ViewStudentDTO>{

    private final ModelMapper mapper;

    @Override
    public StudentEntity toAddEntity(AddStudentDTO addStudentDTO) {
        StudentEntity studentEntity = mapper.map(addStudentDTO, StudentEntity.class);
        studentEntity.setGender(addStudentDTO.getGenderString()
                .equals("MALE") ? Gender.MALE : Gender.FEMALE);
        studentEntity.setBirthDay(new Date(addStudentDTO.getBirthDayTimeStamp()));
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
