package com.example.university_system.service.impl;

import com.example.university_system.component.maper.ProfessorMapper;
import com.example.university_system.dto.RegisterUserResponseDto;
import com.example.university_system.dto.professor.AddProfessorDTO;
import com.example.university_system.dto.professor.UpdateProfessorDTO;
import com.example.university_system.dto.security.authentication.response.AuthenticationResponseDto;
import com.example.university_system.enums.Messages;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.repository.user.ProfessorRepository;
import com.example.university_system.service.BaseService;
import com.example.university_system.service.security.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.university_system.component.CheckRequestsInputStringParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ProfessorService extends BaseService<ProfessorEntity, Long, UpdateProfessorDTO> {

    private final ProfessorRepository professorRepository;
    private final CheckRequestsInputStringParameter stringParameterChecker;
    private final AuthenticationService authenticationService;
    private final ProfessorMapper professorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected ProfessorRepository getRepository() {
        return professorRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.PROFESSOR_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "professor";
    }

    @Override
    public String getAllCacheName() {
        return "allProfessor";
    }


    @Override
    protected Map<Messages, Function<Object, Optional<ProfessorEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<ProfessorEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.PROFESSOR_AVAILABLE_BY_NATIONALCODE, nationalCode -> professorRepository.findByNationalCode((String) nationalCode));
        checkers.put(Messages.PROFESSOR_AVAILABLE_BY_USERNAME, username -> professorRepository.findByUsername((String) username));
        checkers.put(Messages.PROFESSOR_AVAILABLE_BY_CODE, code -> professorRepository.findByCode((int) code));
        return checkers;
    }

    @Override
    protected Object getFieldValue(ProfessorEntity entity, Messages message) {
        switch (message) {
            case PROFESSOR_AVAILABLE_BY_NATIONALCODE:
                return entity.getNationalCode();
            case PROFESSOR_AVAILABLE_BY_USERNAME:
                return entity.getUsername();
            case PROFESSOR_AVAILABLE_BY_CODE:
                return entity.getCode();
            default:
                return null;
        }
    }

    @Override
    protected void updateEntity(UpdateProfessorDTO dto, ProfessorEntity existingEntity) {
        if (stringParameterChecker.checkRequestsInputStringParameter(dto.getName())) existingEntity.setName(dto.getName());
        if (stringParameterChecker.checkRequestsInputStringParameter(dto.getFamily())) existingEntity.setFamily(dto.getFamily());
        if (dto.getPassword() != null) existingEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (dto.getAcademicRank() != null) existingEntity.setAcademicRank(dto.getAcademicRank());
    }



    public ProfessorEntity findByCode(int code) {
        return findByField(code, professorRepository::findByCode);
    }

    public List<CourseEntity> listCoursesProfessor(int code) {
        ProfessorEntity professor = findByCode(code);
        return professor.getCourses().stream().toList();
    }


    @CacheEvict(cacheNames = {"professor", "allProfessor"},
            allEntries = true,
            cacheResolver = "cacheResolver")
    public RegisterUserResponseDto register(AddProfessorDTO addProfessorDTO) {
        AuthenticationResponseDto authenticationResponse = authenticationService.register(addProfessorDTO, professorMapper, professorRepository);
        ProfessorEntity professor = findByCode(addProfessorDTO.getCode());
        return RegisterUserResponseDto.builder()
                .user(professorMapper.toViewDto(professor))
                .authentication(authenticationResponse)
                .build();
    }
}