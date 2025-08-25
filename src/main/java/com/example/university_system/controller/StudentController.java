package com.example.university_system.controller;

import com.example.university_system.service.StudentService;
import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.dto.student.AddStudentDTO;
import com.example.university_system.dto.student.UpdateStudentDTO;
import com.example.university_system.dto.student.ViewStudentDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.component.maper.CourseMapper;
import com.example.university_system.component.maper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/v1")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewStudentDTO save(@RequestBody AddStudentDTO addStudentDTO) {
        StudentEntity studentEntity = studentService.save(studentMapper.toEntity(addStudentDTO));
        return studentMapper.toViewDto(studentEntity);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ViewStudentDTO update(@RequestBody UpdateStudentDTO updateStudentDTO) {
        StudentEntity studentEntity = studentService.update(studentMapper.toEntity(updateStudentDTO));
        return studentMapper.toViewDto(studentEntity);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public ViewStudentDTO findById(@PathVariable Long id) {
        return studentMapper.toViewDto(studentService.findById(id));
    }

    @GetMapping("/list")
    public List<ViewStudentDTO> findAll() {
        return studentMapper.toListViewDTO(studentService.findAll());
    }

    @GetMapping("/{stdNumber}/course/list")
    public List<ViewCourseDTO> listCoursesStudent(@PathVariable long stdNumber) {
        List<CourseEntity> cours = studentService.listCoursesStudent(stdNumber);
        return courseMapper.toListViewDTO(cours);
    }
}
