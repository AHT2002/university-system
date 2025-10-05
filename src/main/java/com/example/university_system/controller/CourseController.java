package com.example.university_system.controller;

import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.enums.Permission;
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
@RequestMapping("${application_controllers_CourseController}")
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

    @Override
    protected Permission getCreatePermission() {
        return Permission.COURSE_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.COURSE_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.COURSE_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.COURSE_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.COURSE_DELETE;
    }

    @Override
    protected void checkMonoAndAllPermissionWithOwnership(Long resourceId, Permission permission, Permission allPermission) {
        checkPermission(permission);
    }

    @Override
    protected void checkMonoPermissionWithOwnership(Long resourceId, Permission permission) {
        checkPermission(permission);
    }


    @GetMapping("/code/{code}")
    public ViewCourseDTO findByCode(@PathVariable int code) {
        this.checkPermission(Permission.COURSE_READ);

        return courseMapper.toViewDto(courseService.findByCode(code));
    }

    @GetMapping("/{codeCourse}/students")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStudentDTO> listStudents(@PathVariable int codeCourse) {
        this.checkPermission(Permission.STUDENT_COURSE_READ);

        List<StudentEntity> studentEntities = courseService.listStudents(codeCourse);
        return studentMapper.toListViewDTO(studentEntities);
    }

    @PostMapping("/{codeCourse}/students/add/{stdNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void addStudent(@PathVariable int codeCourse, @PathVariable long stdNumber) {
        this.checkPermission(Permission.STUDENT_COURSE_CREATE);

        courseService.addStudent(codeCourse, stdNumber);
    }

    @DeleteMapping("/{codeCourse}/students/delete/{stdNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void removeProfessor(@PathVariable int codeCourse, @PathVariable long stdNumber) {
        this.checkPermission(Permission.STUDENT_COURSE_DELETE);

        courseService.removeStudent(codeCourse, stdNumber);
    }

    @PostMapping("/{codeCourse}/professor/assign/{codeProfessor}")
    @ResponseStatus(HttpStatus.OK)
    public void assignProfessor(@PathVariable int codeCourse, @PathVariable int codeProfessor) {
        this.checkPermission(Permission.PROFESSOR_COURSE_CREATE);

        courseService.assignProfessor(codeCourse, codeProfessor);
    }

    @DeleteMapping("/{codeCourse}/professor/remove")
    @ResponseStatus(HttpStatus.OK)
    public void removeProfessor(@PathVariable int codeCourse) {
        this.checkPermission(Permission.PROFESSOR_COURSE_DELETE);

        courseService.removeProfessor(codeCourse);
    }

    @GetMapping("/{codeCourse}/professor")
    public ViewProfessorDTO getProfessor(@PathVariable int codeCourse) {
        this.checkPermission(Permission.PROFESSOR_COURSE_READ);

        ProfessorEntity professorEntity = courseService.getProfessor(codeCourse);
        return professorMapper.toViewDto(professorEntity);
    }

}
