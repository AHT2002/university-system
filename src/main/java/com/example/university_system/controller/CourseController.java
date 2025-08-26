package com.example.university_system.controller;


// <<<<<<< refactor/service-layer-refactor
import com.example.university_system.service.impl.CourseService;
// =======
// import com.example.university_system.service.CourseService;
// >>>>>>> main
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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/v1/")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final StudentMapper studentMapper;
    private final ProfessorMapper professorMapper;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewCourseDTO save(@RequestBody AddCourseDTO addCourseDTO) {
        CourseEntity courseEntity = courseService.save(courseMapper.toEntity(addCourseDTO));
        return courseMapper.toViewDto(courseEntity);
    }

//    @PutMapping("/update")
//    @ResponseStatus(HttpStatus.OK)
//    public ViewCourseDTO update(@RequestBody UpdateCourseDTO updateCourseDTO) {
//        CourseEntity courseEntity = courseService.update(courseMapper.toEntity(updateCourseDTO));
//        return courseMapper.toViewDto(courseEntity);
//    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ViewCourseDTO update(@RequestBody UpdateCourseDTO updateCourseDTO) {
        CourseEntity courseEntity = courseMapper.toEntity(updateCourseDTO);
        return courseMapper.toViewDto(courseService.update(courseEntity, updateCourseDTO.getId()));
    }


    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        courseService.deleteById(id);
    }

    @GetMapping("/find/{id}")
    public ViewCourseDTO findById(@PathVariable Long id) {
        return courseMapper.toViewDto(courseService.findById(id));
    }

    @GetMapping("/list")
    public List<ViewCourseDTO> findAll() {
        return courseMapper.toListViewDTO(courseService.findAll());
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
    public void removeStudent(@PathVariable int codeCourse, @PathVariable long stdNumber) {
        courseService.removeStudent(codeCourse, stdNumber);
    }

    @PostMapping("/{codeCourse}/professor/add/{codeProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public void addProfessor(@PathVariable int codeCourse, @PathVariable int codeProfessor) {
        courseService.addProfessor(codeCourse, codeProfessor);
    }

    @DeleteMapping("/{codeCourse}/professor/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeStudent(@PathVariable int codeCourse) {
        courseService.removeProfessor(codeCourse);
    }

    @GetMapping("/{codeCourse}/professor")
    public ViewProfessorDTO getProfessor(@PathVariable int codeCourse) {
        ProfessorEntity professorEntity = courseService.getProfessor(codeCourse);
        return professorMapper.toViewDto(professorEntity);
    }

}
