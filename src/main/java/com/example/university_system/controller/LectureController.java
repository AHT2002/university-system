package com.example.university_system.controller;

import com.example.university_system.component.maper.LectureMapper;
import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.lecture.AddLectureDTO;
import com.example.university_system.dto.lecture.UpdateLectureDTO;
import com.example.university_system.dto.lecture.ViewLectureDTO;
import com.example.university_system.entity.LectureEntity;
import com.example.university_system.service.impl.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lecture/v1")
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
}
