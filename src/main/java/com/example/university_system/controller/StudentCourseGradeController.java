package com.example.university_system.controller;

import com.example.university_system.component.maper.StudentMapper;
import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.StudentCourseGrade.AddGradeDTO;
import com.example.university_system.dto.StudentCourseGrade.UpdateGradeDTO;
import com.example.university_system.dto.StudentCourseGrade.ViewGradeDTO;
import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.service.impl.StudentCourseGradeService;
import com.example.university_system.component.maper.GradeMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/grades")
@AllArgsConstructor
public class StudentCourseGradeController extends BaseController<StudentCourseGradeEntity, Long, AddGradeDTO, UpdateGradeDTO, ViewGradeDTO> {

    private final StudentCourseGradeService studentCourseGradeService;
    private final GradeMapper gradeMapper;
    private final StudentMapper studentMapper;

    @Override
    protected StudentCourseGradeService getService() {
        return studentCourseGradeService;
    }

    @Override
    protected GradeMapper getMapper() {
        return gradeMapper;
    }



}