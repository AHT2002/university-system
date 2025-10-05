package com.example.university_system.controller;

import com.example.university_system.component.maper.LectureMapper;
import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.lecture.AddLectureDTO;
import com.example.university_system.dto.lecture.UpdateLectureDTO;
import com.example.university_system.dto.lecture.ViewLectureDTO;
import com.example.university_system.entity.LectureEntity;
import com.example.university_system.enums.Permission;
import com.example.university_system.service.impl.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${application_controllers_LectureController}")
@RequiredArgsConstructor
public class LectureController extends BaseController<
        LectureEntity, Long, AddLectureDTO, UpdateLectureDTO, ViewLectureDTO> {

    private final LectureService lectureService;
    private final LectureMapper lectureMapper;

    @Override
    protected LectureService getService() {
        return lectureService;
    }

    @Override
    protected LectureMapper getMapper() {
        return lectureMapper;
    }

    @Override
    protected Permission getCreatePermission() {
        return Permission.LECTURE_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.LECTURE_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.LECTURE_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.LECTURE_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.LECTURE_DELETE;
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
