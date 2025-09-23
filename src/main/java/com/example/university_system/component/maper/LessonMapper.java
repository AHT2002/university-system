package com.example.university_system.component.maper;

import com.example.university_system.dto.lesson.AddLessonDTO;
import com.example.university_system.dto.lesson.ViewLessonDTO;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.service.impl.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LessonMapper implements BaseMapper<LessonEntity, AddLessonDTO, ViewLessonDTO>{
    private final ModelMapper mapper;
    private final DepartmentService departmentService;

    @Override
    public LessonEntity toAddEntity(AddLessonDTO addLessonDTO) {
        LessonEntity lessonEntity = mapper.map(addLessonDTO, LessonEntity.class);
        lessonEntity.setDepartmentEntity(departmentService.findById(addLessonDTO.getDepartmentId()));
        return lessonEntity;
    }


    @Override
    public ViewLessonDTO toViewDto(LessonEntity lessonEntity) {
        ViewLessonDTO viewLessonDTO = mapper.map(lessonEntity, ViewLessonDTO.class);
        if (lessonEntity.getDepartmentEntity() != null) {
            viewLessonDTO.setDepartmentName(lessonEntity.getDepartmentEntity().getName());
        }
        return viewLessonDTO;
    }

    @Override
    public List<ViewLessonDTO> toListViewDTO(List<LessonEntity> lessonEntityList) {
        return lessonEntityList.stream().map(this::toViewDto).toList();
    }
}