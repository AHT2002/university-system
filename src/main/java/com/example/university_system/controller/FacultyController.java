package com.example.university_system.controller;

import com.example.university_system.component.maper.FacultyMapper;
import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.faculty.AddFacultyDTO;
import com.example.university_system.dto.faculty.UpdateFacultyDTO;
import com.example.university_system.dto.faculty.ViewFacultyDTO;
import com.example.university_system.entity.FacultyEntity;
import com.example.university_system.enums.Permission;
import com.example.university_system.service.impl.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${application_controllers_FacultyController}")
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

    @Override
    protected Permission getCreatePermission() {
        return Permission.FACULTY_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.FACULTY_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.FACULTY_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.FACULTY_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.FACULTY_DELETE;
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
