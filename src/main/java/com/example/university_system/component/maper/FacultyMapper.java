package com.example.university_system.component.maper;

import com.example.university_system.dto.faculty.AddFacultyDTO;
import com.example.university_system.dto.faculty.ViewFacultyDTO;
import com.example.university_system.entity.FacultyEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.service.impl.ProfessorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FacultyMapper implements BaseMapper<FacultyEntity, AddFacultyDTO, ViewFacultyDTO>{
    private final ModelMapper mapper;
    private final ProfessorService professorService;

    @Override
    public FacultyEntity toAddEntity(AddFacultyDTO addFacultyDTO) {
        FacultyEntity facultyEntity = mapper.map(addFacultyDTO, FacultyEntity.class);
        ProfessorEntity manager = professorService.findByCode(addFacultyDTO.getManagerCode());
        ProfessorEntity assistant = professorService.findByCode(addFacultyDTO.getAssistantCode());
        facultyEntity.setManager(manager);
        facultyEntity.setAssistant(assistant);
        return facultyEntity;
    }

    @Override
    public ViewFacultyDTO toViewDto(FacultyEntity facultyEntity) {
        ViewFacultyDTO dto = mapper.map(facultyEntity, ViewFacultyDTO.class);
        dto.setManagerCode(facultyEntity.getManager().getCode());
        dto.setManagerName(facultyEntity.getManager().getName() + " " + facultyEntity.getManager().getFamily());
        dto.setAssistantCode(facultyEntity.getAssistant().getCode());
        dto.setAssistantName(facultyEntity.getAssistant().getName() + " " + facultyEntity.getAssistant().getFamily());
        return dto;
    }

    @Override
    public List<ViewFacultyDTO> toListViewDTO(List<FacultyEntity> facultyEntityList) {
        return facultyEntityList.stream().map(this::toViewDto).toList();
    }
}
