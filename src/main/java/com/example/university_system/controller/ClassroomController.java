package com.example.university_system.controller;


import com.example.university_system.component.maper.ClassroomMapper;
import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.classroom.AddClassroomDTO;
import com.example.university_system.dto.classroom.UpdateClassroomDTO;
import com.example.university_system.dto.classroom.ViewClassroomDTO;
import com.example.university_system.entity.ClassroomEntity;
import com.example.university_system.enums.Permission;
import com.example.university_system.service.impl.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("${application_controllers_ClassroomController}")
@RequiredArgsConstructor
public class ClassroomController extends BaseController<
        ClassroomEntity, Long, AddClassroomDTO, UpdateClassroomDTO, ViewClassroomDTO> {

    private final ClassroomService classroomService;
    private final ClassroomMapper classroomMapper;

    @Override
    protected ClassroomService getService() {
        return classroomService;
    }

    @Override
    protected ClassroomMapper getMapper() {
        return classroomMapper;
    }

    @Override
    protected Permission getCreatePermission() {
        return Permission.CLASSROOM_CREATE;
    }

    @Override
    protected Permission getReadPermission() {
        return Permission.CLASSROOM_READ;
    }

    @Override
    protected Permission getReadAllPermission() {
        return Permission.CLASSROOM_READ_ALL;
    }

    @Override
    protected Permission getUpdatePermission() {
        return Permission.CLASSROOM_UPDATE;
    }

    @Override
    protected Permission getDeletePermission() {
        return Permission.CLASSROOM_DELETE;
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
