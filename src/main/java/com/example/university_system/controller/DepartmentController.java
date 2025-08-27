package com.example.university_system.controller;

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
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewDepartmentDTO save(@RequestBody AddDepartmentDTO addDepartmentDTO) {
        DepartmentEntity departmentEntity = departmentMapper.toEntity(addDepartmentDTO);
        DepartmentEntity savedEntity = departmentService.save(departmentEntity);
        return departmentMapper.toViewDto(savedEntity);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ViewDepartmentDTO update(@RequestBody UpdateDepartmentDTO updateDepartmentDTO) {
        DepartmentEntity departmentEntity = departmentMapper.toEntity(updateDepartmentDTO);
        DepartmentEntity updatedEntity = departmentService.update(departmentEntity, updateDepartmentDTO.getId());
        return departmentMapper.toViewDto(updatedEntity);
    }

    @GetMapping("/findById")
    public ViewDepartmentDTO findById(@RequestParam Long id) {
        return departmentMapper.toViewDto(departmentService.findById(id));
    }

    @GetMapping("/findByName")
    public ViewDepartmentDTO findByName(@RequestParam String name) {
        return departmentMapper.toViewDto(departmentService.findByName(name));
    }

    @GetMapping("/findAll")
    public List<ViewDepartmentDTO> findAll() {
        return departmentMapper.toListViewDTO(departmentService.findAll());
    }

    @DeleteMapping("/deleteById")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestParam Long id) {
        departmentService.deleteById(id);
    }
}