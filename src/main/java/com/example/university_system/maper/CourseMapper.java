package com.example.university_system.maper;

import com.example.university_system.dto.course.AddCourseDTO;
import com.example.university_system.dto.course.UpdateCourseDTO;
import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.StudentEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CourseMapper {

    private final ModelMapper mapper;

    public CourseEntity toEntity(AddCourseDTO addCourseDTO) {
        return mapper.map(addCourseDTO, CourseEntity.class);
    }

    public CourseEntity toEntity(UpdateCourseDTO updateCourseDTO) {
        return mapper.map(updateCourseDTO, CourseEntity.class);
    }

    public ViewCourseDTO toViewDto(CourseEntity courseEntity) {
        ViewCourseDTO viewCourseDTO = mapper.map(courseEntity, ViewCourseDTO.class);

        if (courseEntity.getProfessorEntity() != null) {
            String professor = courseEntity.getProfessorEntity().getName() + " " + courseEntity.getProfessorEntity().getFamily();
            viewCourseDTO.setNameProfessor(professor);
        }

        List<Long> studentNumbers = courseEntity.getStudentEntities().stream()
                .map(StudentEntity::getStdNumber)
                .toList();

        viewCourseDTO.setStudentNumbers(studentNumbers);

        return viewCourseDTO;
    }

    public List<ViewCourseDTO> toListViewDTO(List<CourseEntity> courseEntityList) {
        return courseEntityList.stream().map(this::toViewDto).toList();
    }
}
