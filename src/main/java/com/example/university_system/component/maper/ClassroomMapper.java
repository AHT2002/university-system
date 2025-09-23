package com.example.university_system.component.maper;

import com.example.university_system.dto.classroom.AddClassroomDTO;
import com.example.university_system.dto.classroom.ViewClassroomDTO;
import com.example.university_system.entity.ClassroomEntity;
import com.example.university_system.entity.FacultyEntity;
import com.example.university_system.service.impl.FacultyService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ClassroomMapper implements BaseMapper<ClassroomEntity, AddClassroomDTO, ViewClassroomDTO>{
    private final ModelMapper modelMapper;
    private final FacultyService facultyService;

    @Override
    public ClassroomEntity toAddEntity(AddClassroomDTO addClassroomDTO){
        ClassroomEntity classroomEntity = modelMapper.map(addClassroomDTO, ClassroomEntity.class);
        FacultyEntity facultyEntity = facultyService.findById(addClassroomDTO.getFacultyId());
        classroomEntity.setFaculty(facultyEntity);
        return classroomEntity;
    }

    @Override
    public ViewClassroomDTO toViewDto(ClassroomEntity classroomEntity) {
        ViewClassroomDTO viewClassroomDTO = modelMapper.map(classroomEntity, ViewClassroomDTO.class);

        if (classroomEntity.getFaculty() != null) {
            viewClassroomDTO.setFacultyId(classroomEntity.getFaculty().getId());
            viewClassroomDTO.setFacultyName(classroomEntity.getFaculty().getName());
        }
        return viewClassroomDTO;
    }

    @Override
    public List<ViewClassroomDTO> toListViewDTO(List<ClassroomEntity> classroomEntityList) {
        return classroomEntityList.stream().map(this::toViewDto).toList();
    }
}
