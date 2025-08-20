package com.example.university_system.Service;

import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.exception.ConflictException;
import com.example.university_system.exception.NotFoundException;
import com.example.university_system.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorEntity save(ProfessorEntity professorEntity) {
        Optional<ProfessorEntity> optional;

        //It checks if this National Code already exists or not
        optional = professorRepository.findByNationalCode(professorEntity.getNationalCode());
        if (optional.isPresent())
//            throw new ConflictException("The professor with the desired National Code is available in the system.");
            throw new ConflictException(Messages.PROFESSOR_AVAILABLE_BY_NATIONALCODE.getDescription());

        optional = professorRepository.findByUsername(professorEntity.getUsername());
        if (optional.isPresent())
//            throw new ConflictException("The professor with the desired username is available in the system.");
            throw new ConflictException(Messages.PROFESSOR_AVAILABLE_BY_USERNAME.getDescription());

        optional = professorRepository.findByCode(professorEntity.getCode());
        if (optional.isPresent())
//            throw new ConflictException("The professor with the desired code is available in the system.");
            throw new ConflictException(Messages.PROFESSOR_AVAILABLE_BY_CODE.getDescription());

        return professorRepository.save(professorEntity);
    }

    public ProfessorEntity update(ProfessorEntity professorEntityUpdate) {
        ProfessorEntity professorEntity = findById(professorEntityUpdate.getId());

        professorEntityUpdate.setNationalCode(professorEntity.getNationalCode());
        professorEntityUpdate.setUsername(professorEntity.getUsername());
        professorEntityUpdate.setCode(professorEntity.getCode());

        return professorRepository.save(professorEntityUpdate);
    }

    public void deleteById(Long id) {
        findById(id);
        professorRepository.deleteById(id);
    }

    public ProfessorEntity findById(Long id) {
        Optional<ProfessorEntity> optional = professorRepository.findById(id);
        if (optional.isEmpty())
//            throw new NotFoundException("Professor Not found.");
            throw new NotFoundException(Messages.PROFESSOR_NOT_FOUND.getDescription());
        return optional.get();
    }

    public ProfessorEntity findByCode(int code) {
        Optional<ProfessorEntity> optional = professorRepository.findByCode(code);
        if (optional.isEmpty())
//            throw new NotFoundException("Professor Not found.");
            throw new NotFoundException(Messages.PROFESSOR_NOT_FOUND.getDescription());
        return optional.get();
    }

    public List<ProfessorEntity> findAll() {
        return professorRepository.findAll();
    }

    public List<CourseEntity> listCoursesProfessor(int code) {
        ProfessorEntity professorEntity = findByCode(code);
        return professorEntity.getCourses().stream().toList();
    }
}
