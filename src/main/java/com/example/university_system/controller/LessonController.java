package com.example.university_system.controller;

import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.lesson.AddLessonDTO;
import com.example.university_system.dto.lesson.UpdateLessonDTO;
import com.example.university_system.dto.lesson.ViewLessonDTO;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.enums.Permission;
import com.example.university_system.service.impl.LessonService;
import com.example.university_system.component.maper.LessonMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("${application_controllers_LessonController}")
@AllArgsConstructor
public class LessonController extends BaseController<
        LessonEntity, Long, AddLessonDTO, UpdateLessonDTO, ViewLessonDTO
        > {

    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @Override
    protected LessonService getService() {
        return lessonService;
    }

    @Override
    protected LessonMapper getMapper() {
        return lessonMapper;
    }

    @Override
    protected Permission getCreatePermission() {
        return Permission.LESSON_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.LESSON_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.LESSON_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.LESSON_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.LESSON_DELETE;
    }

    @Override
    protected void checkMonoAndAllPermissionWithOwnership(Long resourceId, Permission permission, Permission allPermission) {
        checkPermission(permission);
    }

    @Override
    protected void checkMonoPermissionWithOwnership(Long resourceId, Permission permission) {
        checkPermission(permission);
    }


    @GetMapping("/findByCode")
    public ViewLessonDTO findByCode(@RequestParam int code) {
        this.checkPermission(Permission.LESSON_READ);

        return lessonMapper.toViewDto(lessonService.findByLessonCode(code));
    }

    @PostMapping("/setDepartment")
    @ResponseStatus(HttpStatus.OK)
    public void setDepartment(@RequestParam int lessonCode, @RequestParam String departmentName) {
        this.checkPermission(Permission.LESSON_UPDATE);

        lessonService.setDepartment(lessonCode, departmentName);
    }
}