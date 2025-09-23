package com.example.university_system.component.maper;

import com.example.university_system.dto.lecture.AddLectureDTO;
import com.example.university_system.dto.lecture.ViewLectureDTO;
import com.example.university_system.entity.ClassroomEntity;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.LectureEntity;
import com.example.university_system.service.impl.ClassroomService;
import com.example.university_system.service.impl.CourseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LectureMapper implements BaseMapper<LectureEntity, AddLectureDTO, ViewLectureDTO>{
    private final ModelMapper mapper;
    private final ClassroomService classroomService;
    private final CourseService courseService;

    @Override
    public LectureEntity toAddEntity(AddLectureDTO addLectureDTO) {
        LectureEntity lectureEntity = mapper.map(addLectureDTO, LectureEntity.class);
        ClassroomEntity classroom = classroomService.findByCode(addLectureDTO.getClassroomCode());
        CourseEntity course = courseService.findByCode(addLectureDTO.getCourseCode());
        lectureEntity.setClassroom(classroom);
        lectureEntity.setCourse(course);
        return lectureEntity;
    }

    @Override
    public ViewLectureDTO toViewDto(LectureEntity lectureEntity) {
        ViewLectureDTO viewLectureDTO = mapper.map(lectureEntity, ViewLectureDTO.class);
        viewLectureDTO.setClassTime(lectureEntity.getClassTime().getTitle());
        viewLectureDTO.setDayOfWeek(lectureEntity.getDayOfWeek().getTitle());

        if (lectureEntity.getClassroom() != null) {
            viewLectureDTO.setClassroomCode(lectureEntity.getClassroom().getCode());
            viewLectureDTO.setFacultyName(lectureEntity.getClassroom().getFaculty().getName());
        }

        if(lectureEntity.getCourse() != null) {
            viewLectureDTO.setCourseCode(lectureEntity.getCourse().getCode());
            viewLectureDTO.setSemester(lectureEntity.getCourse().getSemester());
            viewLectureDTO.setLessonCode(lectureEntity.getCourse().getLessonEntity().getLessonCode());
            viewLectureDTO.setLessonTitle(lectureEntity.getCourse().getLessonEntity().getTitle());
            viewLectureDTO.setDepartmentName(lectureEntity.getCourse().getLessonEntity().getDepartmentEntity().getName());

            if (lectureEntity.getCourse().getProfessorEntity() != null){
                viewLectureDTO.setProfessorCode(lectureEntity.getCourse().getProfessorEntity().getCode());
                viewLectureDTO.setProfessorName(lectureEntity.getCourse().getProfessorEntity().getName());
                viewLectureDTO.setProfessorFamily(lectureEntity.getCourse().getProfessorEntity().getFamily());
            }
        }
        return viewLectureDTO;
    }

    @Override
    public List<ViewLectureDTO> toListViewDTO(List<LectureEntity> lectureEntityList) {
        return lectureEntityList.stream().map(this::toViewDto).toList();
    }
}
