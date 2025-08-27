package com.example.university_system.component.maper;

import com.example.university_system.dto.lesson.AddLessonDTO;
import com.example.university_system.dto.lesson.UpdateLessonDTO;
import com.example.university_system.dto.lesson.ViewLessonDTO;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.service.impl.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class LessonMapper {

    private final ModelMapper mapper;
    private final DepartmentService departmentService;

    public LessonEntity toEntity(AddLessonDTO addLessonDTO) {
        LessonEntity lessonEntity = mapper.map(addLessonDTO, LessonEntity.class);
        // مپینگ departmentName به DepartmentEntity
        lessonEntity.setDepartmentEntity(departmentService.findById(addLessonDTO.getDepartmentId()));
        return lessonEntity;
    }

    public LessonEntity toEntity(UpdateLessonDTO updateLessonDTO) {
        LessonEntity lessonEntity = mapper.map(updateLessonDTO, LessonEntity.class);
        // مپینگ departmentName به DepartmentEntity
        lessonEntity.setDepartmentEntity(departmentService.findByName(updateLessonDTO.getDepartmentName()));
        return lessonEntity;
    }

    public ViewLessonDTO toViewDto(LessonEntity lessonEntity) {
        ViewLessonDTO viewLessonDTO = mapper.map(lessonEntity, ViewLessonDTO.class);
        // مپینگ دستی برای departmentName
        if (lessonEntity.getDepartmentEntity() != null) {
            viewLessonDTO.setDepartmentName(lessonEntity.getDepartmentEntity().getName());
        }
        return viewLessonDTO;
    }

    public List<ViewLessonDTO> toListViewDTO(List<LessonEntity> lessonEntityList) {
        return lessonEntityList.stream().map(this::toViewDto).toList();
    }
}