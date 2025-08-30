package com.example.university_system.component.maper;

import com.example.university_system.dto.professor.AddProfessorDTO;
import com.example.university_system.dto.professor.UpdateProfessorDTO;
import com.example.university_system.dto.professor.ViewProfessorDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.enums.Gender;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class ProfessorMapper implements BaseMapper<ProfessorEntity, AddProfessorDTO, UpdateProfessorDTO, ViewProfessorDTO>{
    private final ModelMapper mapper;

    @Override
    public ProfessorEntity toAddEntity(AddProfessorDTO addProfessorDTO) {
        ProfessorEntity professorEntity = mapper.map(addProfessorDTO, ProfessorEntity.class);
        professorEntity.setGender(addProfessorDTO.getGenderString()
                .equals("MALE") ? Gender.MALE : Gender.FEMALE);
        professorEntity.setBirthDay(new Date(addProfessorDTO.getBirthDayTimeStamp()));
        return professorEntity;
    }

    @Override
    public ProfessorEntity toUpdateEntity(UpdateProfessorDTO updateProfessorDTO) {
        ProfessorEntity professorEntity = mapper.map(updateProfessorDTO, ProfessorEntity.class);
        professorEntity.setGender(updateProfessorDTO.getGenderString()
                .equals("MALE") ? Gender.MALE : Gender.FEMALE);
        professorEntity.setBirthDay(new Date(updateProfessorDTO.getBirthDayTimeStamp()));
        return professorEntity;
    }

    @Override
    public ViewProfessorDTO toViewDto(ProfessorEntity professorEntity) {
        ViewProfessorDTO viewProfessorDTO = mapper.map(professorEntity, ViewProfessorDTO.class);

        List<Integer> coursesCode = professorEntity.getCourses().stream()
                .map(CourseEntity::getCode)
                .toList();
        viewProfessorDTO.setCourseCodes(coursesCode);

        viewProfessorDTO.setBirthDayTimeStamp(professorEntity.getBirthDay().getTime());

        viewProfessorDTO.setGenderString(professorEntity.getGender().name());

        return viewProfessorDTO;
    }

    @Override
    public List<ViewProfessorDTO> toListViewDTO(List<ProfessorEntity> professorEntityList) {
        return professorEntityList.stream().map(this::toViewDto).toList();
    }
}
