package com.example.university_system.controller;

import com.example.university_system.controller.Base.BaseController;
import com.example.university_system.dto.lesson.AddLessonDTO;
import com.example.university_system.dto.lesson.UpdateLessonDTO;
import com.example.university_system.dto.lesson.ViewLessonDTO;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.service.impl.LessonService;
import com.example.university_system.component.maper.LessonMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/lesson/v1")
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

    @GetMapping("/findByCode")
    public ViewLessonDTO findByCode(@RequestParam int code) {
        return lessonMapper.toViewDto(lessonService.findByLessonCode(code));
    }

    @PostMapping("/setDepartment")
    @ResponseStatus(HttpStatus.OK)
    public void setDepartment(@RequestParam int lessonCode, @RequestParam String departmentName) {
        lessonService.setDepartment(lessonCode, departmentName);
    }
}