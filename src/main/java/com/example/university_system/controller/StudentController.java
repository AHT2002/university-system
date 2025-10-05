package com.example.university_system.controller;


import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.RegisterUserResponseDto;
import com.example.university_system.enums.Permission;
import com.example.university_system.repository.user.StudentRepository;
import com.example.university_system.service.impl.StudentService;

import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.dto.student.AddStudentDTO;
import com.example.university_system.dto.student.UpdateStudentDTO;
import com.example.university_system.dto.student.ViewStudentDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.component.maper.CourseMapper;
import com.example.university_system.component.maper.StudentMapper;
import com.example.university_system.service.impl.UserService;
import com.example.university_system.service.security.AuthenticationService;
import com.example.university_system.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${application_controllers_StudentController}")
@RequiredArgsConstructor
public class StudentController extends BaseController<StudentEntity, Long, AddStudentDTO, UpdateStudentDTO, ViewStudentDTO> {

    private final StudentService studentService;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected StudentService getService() {
        return studentService;
    }

    @Override
    protected StudentMapper getMapper() {
        return studentMapper;
    }

    @Override
    protected Permission getCreatePermission() {
        return Permission.STUDENT_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.STUDENT_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.STUDENT_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.STUDENT_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.STUDENT_DELETE;
    }


    @Override
    protected void checkMonoAndAllPermissionWithOwnership(Long resourceId, Permission permission, Permission allPermission) {
        String currentUserNationalCode = jwtService.extractNationalCodeFromToken()
                .orElseThrow(() -> new RuntimeException("National code not found in token"));
        long currentUserId = userService.findByNationalCode(currentUserNationalCode).getId();

        this.checkMonoAndAllPermissionWithOwnership(
                allPermission,
                permission,
                resourceId,
                currentUserId
        );
    }

    @Override
    protected void checkMonoPermissionWithOwnership(Long resourceId, Permission permission) {
        String currentUserNationalCode = jwtService.extractNationalCodeFromToken()
                .orElseThrow(() -> new RuntimeException("National code not found in token"));
        long currentUserId = userService.findByNationalCode(currentUserNationalCode).getId();

        this.checkMonoPermissionWithOwnership(
                permission,
                resourceId,
                currentUserId
        );
    }



    @GetMapping("/{stdNumber}/course/list")
    public List<ViewCourseDTO> listCoursesStudent(@PathVariable long stdNumber) {
        this.checkPermission(Permission.STUDENT_READ_ALL_COURSES);

        List<CourseEntity> cours = studentService.listCoursesStudent(stdNumber);
        return courseMapper.toListViewDTO(cours);
    }

    @Override
    public ViewStudentDTO save(AddStudentDTO addStudentDTO) {
        return null;
    }

    @PostMapping("/register")
    public RegisterUserResponseDto register(AddStudentDTO addStudentDTO) {
        return studentService.register(addStudentDTO);
    }
}
