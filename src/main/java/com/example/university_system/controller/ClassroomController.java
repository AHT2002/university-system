package com.example.university_system.controller;


import com.example.university_system.component.maper.ClassroomMapper;
import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.classroom.AddClassroomDTO;
import com.example.university_system.dto.classroom.UpdateClassroomDTO;
import com.example.university_system.dto.classroom.ViewClassroomDTO;
import com.example.university_system.entity.ClassroomEntity;
import com.example.university_system.service.impl.ClassroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/classroom/v1/")
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
}
