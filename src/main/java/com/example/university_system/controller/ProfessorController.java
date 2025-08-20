package com.example.university_system.controller;

import com.example.university_system.Service.ProfessorService;
import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.dto.professor.AddProfessorDTO;
import com.example.university_system.dto.professor.UpdateProfessorDTO;
import com.example.university_system.dto.professor.ViewProfessorDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.maper.CourseMapper;
import com.example.university_system.maper.ProfessorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor/v1")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;
    private final CourseMapper courseMapper;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewProfessorDTO save(@RequestBody AddProfessorDTO addProfessorDTO) {
        ProfessorEntity professorEntity = professorService.save(professorMapper.toEntity(addProfessorDTO));
        return professorMapper.toViewDto(professorEntity);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ViewProfessorDTO update(@RequestBody UpdateProfessorDTO updateProfessorDTO) {
        ProfessorEntity professorEntity = professorService.save(professorMapper.toEntity(updateProfessorDTO));
        return professorMapper.toViewDto(professorEntity);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        professorService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public ViewProfessorDTO findById(@PathVariable Long id) {
        return professorMapper.toViewDto(professorService.findById(id));
    }

    @GetMapping("/list")
    public List<ViewProfessorDTO> findAll() {
        return professorMapper.toListViewDTO(professorService.findAll());
    }

    @GetMapping("/{codeProfessor}/course/list")
    public List<ViewCourseDTO> listCoursesProfessor(@PathVariable int codeProfessor) {
        List<CourseEntity> cours = professorService.listCoursesProfessor(codeProfessor);
        return courseMapper.toListViewDTO(cours);
    }
}
