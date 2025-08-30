package com.example.university_system.controller;



import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.service.impl.CourseService;

import com.example.university_system.dto.course.AddCourseDTO;
import com.example.university_system.dto.course.UpdateCourseDTO;
import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.dto.professor.ViewProfessorDTO;
import com.example.university_system.dto.student.ViewStudentDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.component.maper.CourseMapper;
import com.example.university_system.component.maper.ProfessorMapper;
import com.example.university_system.component.maper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/v1/")
@RequiredArgsConstructor
public class CourseController extends BaseController<
        CourseEntity, Long, AddCourseDTO, UpdateCourseDTO, ViewCourseDTO
        > {

    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;
    private final ProfessorMapper professorMapper;

    @Override
    protected CourseService getService() {
        return courseService;
    }

    @Override
    protected CourseMapper getMapper() {
        return courseMapper;
    }


    @GetMapping("/code/{code}")
    public ViewCourseDTO findByCode(@PathVariable int code) {
        return courseMapper.toViewDto(courseService.findByCode(code));
    }

    @GetMapping("/{codeCourse}/students")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStudentDTO> listStudents(@PathVariable int codeCourse) {
        List<StudentEntity> studentEntities = courseService.listStudents(codeCourse);
        return studentMapper.toListViewDTO(studentEntities);
    }

    @PostMapping("/{codeCourse}/students/add/{stdNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void addStudent(@PathVariable int codeCourse, @PathVariable long stdNumber) {
        courseService.addStudent(codeCourse, stdNumber);
    }

    @DeleteMapping("/{codeCourse}/students/delete/{stdNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void removeProfessor(@PathVariable int codeCourse, @PathVariable long stdNumber) {
        courseService.removeStudent(codeCourse, stdNumber);
    }

    @PostMapping("/{codeCourse}/professor/assign/{codeProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public void assignProfessor(@PathVariable int codeCourse, @PathVariable int codeProfessor) {
        courseService.assignProfessor(codeCourse, codeProfessor);
    }

    @DeleteMapping("/{codeCourse}/professor/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeProfessor(@PathVariable int codeCourse) {
        courseService.removeProfessor(codeCourse);
    }

    @GetMapping("/{codeCourse}/professor")
    public ViewProfessorDTO getProfessor(@PathVariable int codeCourse) {
        ProfessorEntity professorEntity = courseService.getProfessor(codeCourse);
        return professorMapper.toViewDto(professorEntity);
    }

}
