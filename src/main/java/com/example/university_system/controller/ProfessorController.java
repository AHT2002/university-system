package com.example.university_system.controller;

import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.RegisterUserResponseDto;
import com.example.university_system.enums.Permission;
import com.example.university_system.service.impl.ProfessorService;

import com.example.university_system.dto.course.ViewCourseDTO;
import com.example.university_system.dto.professor.AddProfessorDTO;
import com.example.university_system.dto.professor.UpdateProfessorDTO;
import com.example.university_system.dto.professor.ViewProfessorDTO;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.component.maper.CourseMapper;
import com.example.university_system.component.maper.ProfessorMapper;
import com.example.university_system.service.impl.UserService;
import com.example.university_system.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${application_controllers_ProfessorController}")
@RequiredArgsConstructor
public class ProfessorController extends BaseController<
        ProfessorEntity, Long, AddProfessorDTO, UpdateProfessorDTO, ViewProfessorDTO
        > {
    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;
    private final CourseMapper courseMapper;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected ProfessorService getService() {
        return professorService;
    }

    @Override
    protected ProfessorMapper getMapper() {
        return professorMapper;
    }

    @Override
    protected Permission getCreatePermission() {
        return Permission.PROFESSOR_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.PROFESSOR_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.PROFESSOR_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.PROFESSOR_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.PROFESSOR_DELETE;
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


    @GetMapping("/{codeProfessor}/course/list")
    public List<ViewCourseDTO> listCoursesProfessor(@PathVariable int codeProfessor) {
        this.checkPermission(Permission.PROFESSOR_READ_ALL_COURSES);

        List<CourseEntity> cours = professorService.listCoursesProfessor(codeProfessor);
        return courseMapper.toListViewDTO(cours);
    }

    @Override
    public ViewProfessorDTO save(AddProfessorDTO addProfessorDTO) {
        return null;
    }

    @PostMapping("/register")
    public RegisterUserResponseDto register(AddProfessorDTO addProfessorDTO) {
        return professorService.register(addProfessorDTO);
    }
}
