package com.example.university_system.component.maper;

import com.example.university_system.dto.StudentCourseGrade.AddGradeDTO;
import com.example.university_system.dto.StudentCourseGrade.ViewGradeDTO;
import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.service.impl.StudentService;
import com.example.university_system.service.impl.CourseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class GradeMapper implements BaseMapper<StudentCourseGradeEntity, AddGradeDTO, ViewGradeDTO> {

    private final StudentService studentService;
    private final CourseService courseService;
    private final ModelMapper mapper;

    @Override
    public StudentCourseGradeEntity toAddEntity(AddGradeDTO addDto) {
        StudentCourseGradeEntity entity = new StudentCourseGradeEntity();
        entity.setStudent(studentService.findById(addDto.getStudentId()));
        entity.setCourse(courseService.findById(addDto.getCourseId()));
        entity.setGrade(addDto.getGrade()); // می‌تونه null باشه
        entity.setStatus(addDto.getStatus()); // اجباری
        return entity;
    }

    @Override
    public ViewGradeDTO toViewDto(StudentCourseGradeEntity entity) {
        ViewGradeDTO dto = new ViewGradeDTO();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudent().getId());
        dto.setStudentName(entity.getStudent().getName());
        dto.setStudentLastName(entity.getStudent().getFamily());
        dto.setCourseId(entity.getCourse().getId());
        dto.setCourseCode(entity.getCourse().getCode());
        dto.setLessonTitle(entity.getCourse().getLessonEntity() != null ? entity.getCourse().getLessonEntity().getTitle() : "Null");
        dto.setProfessorName(entity.getCourse().getProfessorEntity() != null ? entity.getCourse().getProfessorEntity().getName() : "Null");
        dto.setUnits(entity.getCourse().getLessonEntity().getUnits());
        dto.setGrade(entity.getGrade());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    @Override
    public List<ViewGradeDTO> toListViewDTO(List<StudentCourseGradeEntity> entities) {
        return entities.stream().map(this::toViewDto).collect(Collectors.toList());
    }
}