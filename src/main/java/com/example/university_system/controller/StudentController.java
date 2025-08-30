package com.example.university_system.controller;


import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.service.impl.StudentService;

import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.dto.student.AddStudentDTO;
import com.example.university_system.dto.student.UpdateStudentDTO;
import com.example.university_system.dto.student.ViewStudentDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.component.maper.CourseMapper;
import com.example.university_system.component.maper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/v1")
@RequiredArgsConstructor
public class StudentController extends BaseController<StudentEntity, Long, AddStudentDTO, UpdateStudentDTO, ViewStudentDTO> {

    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;


    @Override
    protected StudentService getService() {
        return studentService;
    }

    @Override
    protected StudentMapper getMapper() {
        return studentMapper;
    }

    @GetMapping("/{stdNumber}/course/list")
    public List<ViewCourseDTO> listCoursesStudent(@PathVariable long stdNumber) {
        List<CourseEntity> cours = studentService.listCoursesStudent(stdNumber);
        return courseMapper.toListViewDTO(cours);
    }
}
