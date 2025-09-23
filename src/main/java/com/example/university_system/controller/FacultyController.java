package com.example.university_system.controller;

import com.example.university_system.component.maper.CourseMapper;
import com.example.university_system.component.maper.FacultyMapper;
import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.faculty.AddFacultyDTO;
import com.example.university_system.dto.faculty.UpdateFacultyDTO;
import com.example.university_system.dto.faculty.ViewFacultyDTO;
import com.example.university_system.entity.FacultyEntity;
import com.example.university_system.service.impl.CourseService;
import com.example.university_system.service.impl.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/faculty/v1/")
@RequiredArgsConstructor
public class FacultyController extends BaseController
        <FacultyEntity, Long, AddFacultyDTO, UpdateFacultyDTO, ViewFacultyDTO> {

    private final FacultyService facultyService;
    private final FacultyMapper facultyMapper;

    @Override
    protected FacultyService getService() {
        return facultyService;
    }

    @Override
    protected FacultyMapper getMapper() {
        return facultyMapper;
    }


}
