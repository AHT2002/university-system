package com.example.university_system.component.maper;

import com.example.university_system.dto.faculty.AddFacultyDTO;
import com.example.university_system.dto.faculty.ViewFacultyDTO;
import com.example.university_system.entity.FacultyEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FacultyMapper implements BaseMapper<FacultyEntity, AddFacultyDTO, ViewFacultyDTO>{
    private final ModelMapper mapper;

    @Override
    public FacultyEntity toAddEntity(AddFacultyDTO addFacultyDTO) {
        return mapper.map(addFacultyDTO, FacultyEntity.class);
    }

    @Override
    public ViewFacultyDTO toViewDto(FacultyEntity facultyEntity) {
        return mapper.map(facultyEntity, ViewFacultyDTO.class);
    }

    @Override
    public List<ViewFacultyDTO> toListViewDTO(List<FacultyEntity> facultyEntityList) {
        return facultyEntityList.stream().map(this::toViewDto).toList();
    }
}
