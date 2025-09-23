package com.example.university_system.component.maper;

import com.example.university_system.dto.course.AddCourseDTO;
import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.service.impl.LessonService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CourseMapper implements BaseMapper<CourseEntity, AddCourseDTO, ViewCourseDTO>{

    private final ModelMapper mapper;
    private final LessonService lessonService;

    @Override
    public CourseEntity toAddEntity(AddCourseDTO addCourseDTO) {
        CourseEntity courseEntity = mapper.map(addCourseDTO, CourseEntity.class);
        LessonEntity lesson = lessonService.findByLessonCode(addCourseDTO.getLessonCode());
        courseEntity.setLessonEntity(lesson);
        return courseEntity;
    }

    @Override
    public ViewCourseDTO toViewDto(CourseEntity courseEntity) {
        ViewCourseDTO viewCourseDTO = mapper.map(courseEntity, ViewCourseDTO.class);

        if (courseEntity.getProfessorEntity() != null) {
            String professor = courseEntity.getProfessorEntity().getName() + " " + courseEntity.getProfessorEntity().getFamily();
            viewCourseDTO.setProfessorName(professor);
        }

        List<Long> studentNumbers = courseEntity.getStudentGrades().stream()
                .map(StudentCourseGradeEntity::getStudent)
                .map(student -> student.getStdNumber())
                .toList();
        viewCourseDTO.setStudentNumbers(studentNumbers);


        if (courseEntity.getLessonEntity() != null) {
            viewCourseDTO.setLessonTitle(courseEntity.getLessonEntity().getTitle());
            viewCourseDTO.setLessonUnits(courseEntity.getLessonEntity().getUnits());
            if (courseEntity.getLessonEntity().getDepartmentEntity() != null) {
                viewCourseDTO.setDepartmentName(courseEntity.getLessonEntity().getDepartmentEntity().getName());
            }
        }

        return viewCourseDTO;
    }


    @Override
    public List<ViewCourseDTO> toListViewDTO(List<CourseEntity> courseEntityList) {
        return courseEntityList.stream().map(this::toViewDto).toList();
    }
}
