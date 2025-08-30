package com.example.university_system.controller;

import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.service.impl.ProfessorService;

import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.dto.professor.AddProfessorDTO;
import com.example.university_system.dto.professor.UpdateProfessorDTO;
import com.example.university_system.dto.professor.ViewProfessorDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.component.maper.CourseMapper;
import com.example.university_system.component.maper.ProfessorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor/v1")
@RequiredArgsConstructor
public class ProfessorController extends BaseController<
        ProfessorEntity, Long, AddProfessorDTO, UpdateProfessorDTO, ViewProfessorDTO
        > {
    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;
    private final CourseMapper courseMapper;

    @Override
    protected ProfessorService getService() {
        return professorService;
    }

    @Override
    protected ProfessorMapper getMapper() {
        return professorMapper;
    }

    @GetMapping("/{codeProfessor}/course/list")
    public List<ViewCourseDTO> listCoursesProfessor(@PathVariable int codeProfessor) {
        List<CourseEntity> cours = professorService.listCoursesProfessor(codeProfessor);
        return courseMapper.toListViewDTO(cours);
    }
}
