package com.example.university_system.controller;

import com.example.university_system.dto.lesson.AddLessonDTO;
import com.example.university_system.dto.lesson.UpdateLessonDTO;
import com.example.university_system.dto.lesson.ViewLessonDTO;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.service.impl.LessonService;
import com.example.university_system.component.maper.LessonMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson/v1")
@AllArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewLessonDTO save(@RequestBody AddLessonDTO addLessonDTO) {
        LessonEntity lessonEntity = lessonMapper.toEntity(addLessonDTO);
        LessonEntity savedEntity = lessonService.save(lessonEntity);
        return lessonMapper.toViewDto(savedEntity);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ViewLessonDTO update(@RequestBody UpdateLessonDTO updateLessonDTO) {
        LessonEntity lessonEntity = lessonMapper.toEntity(updateLessonDTO);
        LessonEntity updatedEntity = lessonService.update(lessonEntity, updateLessonDTO.getId());
        return lessonMapper.toViewDto(updatedEntity);
    }

    @GetMapping("/findById")
    public ViewLessonDTO findById(@RequestParam Long id) {
        return lessonMapper.toViewDto(lessonService.findById(id));
    }

    @GetMapping("/findByCode")
    public ViewLessonDTO findByCode(@RequestParam int code) {
        return lessonMapper.toViewDto(lessonService.findByLessonCode(code));
    }

    @GetMapping("/findAll")
    public List<ViewLessonDTO> findAll() {
        return lessonMapper.toListViewDTO(lessonService.findAll());
    }

    @DeleteMapping("/deleteById")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestParam Long id) {
        lessonService.deleteById(id);
    }

    @PostMapping("/setDepartment")
    @ResponseStatus(HttpStatus.OK)
    public void setDepartment(@RequestParam int lessonCode, @RequestParam String departmentName) {
        lessonService.setDepartment(lessonCode, departmentName);
    }
}