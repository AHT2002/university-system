package com.example.university_system.controller;

import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.department.AddDepartmentDTO;
import com.example.university_system.dto.department.UpdateDepartmentDTO;
import com.example.university_system.dto.department.ViewDepartmentDTO;
import com.example.university_system.entity.DepartmentEntity;
import com.example.university_system.enums.Permission;
import com.example.university_system.service.impl.DepartmentService;
import com.example.university_system.component.maper.DepartmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${application_controllers_DepartmentController}")
@AllArgsConstructor
public class DepartmentController extends BaseController<
        DepartmentEntity, Long, AddDepartmentDTO, UpdateDepartmentDTO, ViewDepartmentDTO
        > {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @Override
    protected DepartmentService getService() {
        return departmentService;
    }

    @Override
    protected DepartmentMapper getMapper() {
        return departmentMapper;
    }

    @Override
    protected Permission getCreatePermission() {
        return Permission.DEPARTMENT_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.DEPARTMENT_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.DEPARTMENT_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.DEPARTMENT_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.DEPARTMENT_DELETE;
    }

    @Override
    protected void checkMonoAndAllPermissionWithOwnership(Long resourceId, Permission permission, Permission allPermission) {
        checkPermission(permission);
    }

    @Override
    protected void checkMonoPermissionWithOwnership(Long resourceId, Permission permission) {
        checkPermission(permission);
    }


    @GetMapping("/findByName")
    public ViewDepartmentDTO findByName(@RequestParam String name) {
        this.checkPermission(Permission.DEPARTMENT_READ);

        return departmentMapper.toViewDto(departmentService.findByName(name));
    }
}