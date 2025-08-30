package com.example.university_system.controller;

import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.department.AddDepartmentDTO;
import com.example.university_system.dto.department.UpdateDepartmentDTO;
import com.example.university_system.dto.department.ViewDepartmentDTO;
import com.example.university_system.entity.DepartmentEntity;
import com.example.university_system.service.impl.DepartmentService;
import com.example.university_system.component.maper.DepartmentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department/v1")
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

    @GetMapping("/findByName")
    public ViewDepartmentDTO findByName(@RequestParam String name) {
        return departmentMapper.toViewDto(departmentService.findByName(name));
    }
}