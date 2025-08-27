package com.example.university_system.component.maper;

import com.example.university_system.dto.course.AddCourseDTO;
import com.example.university_system.dto.course.UpdateCourseDTO;
import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.service.impl.LessonService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CourseMapper {

    private final ModelMapper mapper;
    private final LessonService lessonService;

//    public CourseEntity toEntity(AddCourseDTO addCourseDTO) {
//        return mapper.map(addCourseDTO, CourseEntity.class);
//    }

    public CourseEntity toEntity(AddCourseDTO addCourseDTO) {
        CourseEntity courseEntity = mapper.map(addCourseDTO, CourseEntity.class);
        // مپینگ lessonCode به LessonEntity
        LessonEntity lesson = lessonService.findByLessonCode(addCourseDTO.getLessonCode());
        courseEntity.setLessonEntity(lesson);
        return courseEntity;
    }

//    public CourseEntity toEntity(UpdateCourseDTO updateCourseDTO) {
//        return mapper.map(updateCourseDTO, CourseEntity.class);
//    }

    public CourseEntity toEntity(UpdateCourseDTO updateCourseDTO) {
        CourseEntity courseEntity = mapper.map(updateCourseDTO, CourseEntity.class);
        // مپینگ lessonCode به LessonEntity
        LessonEntity lesson = lessonService.findByLessonCode(updateCourseDTO.getLessonCode());
        courseEntity.setLessonEntity(lesson);
        return courseEntity;
    }



//    public ViewCourseDTO toViewDto(CourseEntity courseEntity) {
//        ViewCourseDTO viewCourseDTO = mapper.map(courseEntity, ViewCourseDTO.class);
//
//        if (courseEntity.getProfessorEntity() != null) {
//            String professor = courseEntity.getProfessorEntity().getName() + " " + courseEntity.getProfessorEntity().getFamily();
//            viewCourseDTO.setNameProfessor(professor);
//        }
//
//        List<Long> studentNumbers = courseEntity.getStudentEntities().stream()
//                .map(StudentEntity::getStdNumber)
//                .toList();
//
//        viewCourseDTO.setStudentNumbers(studentNumbers);
//
//        return viewCourseDTO;
//    }

    public ViewCourseDTO toViewDto(CourseEntity courseEntity) {
        ViewCourseDTO viewCourseDTO = mapper.map(courseEntity, ViewCourseDTO.class);

        // مپینگ دستی برای nameProfessor
        if (courseEntity.getProfessorEntity() != null) {
            String professor = courseEntity.getProfessorEntity().getName() + " " + courseEntity.getProfessorEntity().getFamily();
            viewCourseDTO.setProfessorName(professor);
        }

        // مپینگ دستی برای studentNumbers
        List<Long> studentNumbers = courseEntity.getStudentEntities().stream()
                .map(StudentEntity::getStdNumber)
                .toList();
        viewCourseDTO.setStudentNumbers(studentNumbers);

        // مپینگ دستی برای lessonTitle, lessonUnits, و departmentName
        if (courseEntity.getLessonEntity() != null) {
            viewCourseDTO.setLessonTitle(courseEntity.getLessonEntity().getTitle());
            viewCourseDTO.setLessonUnits(courseEntity.getLessonEntity().getUnits());
            if (courseEntity.getLessonEntity().getDepartmentEntity() != null) {
                viewCourseDTO.setDepartmentName(courseEntity.getLessonEntity().getDepartmentEntity().getName());
            }
        }

        return viewCourseDTO;
    }



    public List<ViewCourseDTO> toListViewDTO(List<CourseEntity> courseEntityList) {
        return courseEntityList.stream().map(this::toViewDto).toList();
    }
}
