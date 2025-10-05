package com.example.university_system.controller;

import com.example.university_system.component.maper.StudentMapper;
import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.StudentCourseGrade.AddGradeDTO;
import com.example.university_system.dto.StudentCourseGrade.UpdateGradeDTO;
import com.example.university_system.dto.StudentCourseGrade.ViewGradeDTO;
import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.enums.Permission;
import com.example.university_system.service.impl.StudentCourseGradeService;
import com.example.university_system.component.maper.GradeMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${application_controllers_StudentCourseGradeController}")
@AllArgsConstructor
public class StudentCourseGradeController extends BaseController<StudentCourseGradeEntity, Long, AddGradeDTO, UpdateGradeDTO, ViewGradeDTO> {

    private final StudentCourseGradeService studentCourseGradeService;
    private final GradeMapper gradeMapper;

    @Override
    protected StudentCourseGradeService getService() {
        return studentCourseGradeService;
    }

    @Override
    protected GradeMapper getMapper() {
        return gradeMapper;
    }

    @Override
    protected Permission getCreatePermission() {
        return Permission.STUDENT_COURSE_GRADE_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.STUDENT_COURSE_GRADE_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.STUDENT_COURSE_GRADE_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.STUDENT_COURSE_GRADE_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.STUDENT_COURSE_GRADE_DELETE;
    }

    @Override
    protected void checkMonoAndAllPermissionWithOwnership(Long resourceId, Permission permission, Permission allPermission) {
        checkPermission(permission);
    }

    @Override
    protected void checkMonoPermissionWithOwnership(Long resourceId, Permission permission) {
        checkPermission(permission);
    }


}