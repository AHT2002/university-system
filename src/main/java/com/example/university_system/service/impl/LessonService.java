package com.example.university_system.service.impl;

import com.example.university_system.dto.lesson.UpdateLessonDTO;
import com.example.university_system.entity.DepartmentEntity;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.repository.LessonRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class LessonService extends BaseService<LessonEntity, Long, UpdateLessonDTO> {

    private final LessonRepository lessonRepository;
    private final DepartmentService departmentService;

    @Override
    protected LessonRepository getRepository() {
        return lessonRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.LESSON_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "lesson";
    }

    @Override
    public String getAllCacheName() {
        return "allLesson";
    }

    @Override
    protected void updateEntity(UpdateLessonDTO dto, LessonEntity existingEntity) {
        if (dto.getTitle() != null) {
            existingEntity.setTitle(dto.getTitle());
        }
        if (dto.getUnits() != null) {
            existingEntity.setUnits(dto.getUnits());
        }
        if (dto.getDepartmentId() != null) {
            existingEntity.setDepartmentEntity(departmentService.findById(dto.getDepartmentId()));
        }
        if (dto.getPreRequisiteLessonsId() != null) {
            List<LessonEntity> prereqs = dto.getPreRequisiteLessonsId().stream()
                    .map(this::findById)
                    .toList();
            existingEntity.setPrerequisites(prereqs);
        }
        if (dto.getCoRequisiteLessonsId() != null) {
            List<LessonEntity> coreqs = dto.getCoRequisiteLessonsId().stream()
                    .map(this::findById)
                    .toList();
            existingEntity.setCorequisites(coreqs);
        }
    }



    @Override
    protected Map<Messages, Function<Object, Optional<LessonEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<LessonEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.LESSON_CODE_ALREADY_EXISTS, code -> lessonRepository.findByLessonCode((Integer) code));
        return checkers;
    }

    @Override
    protected Object getFieldValue(LessonEntity entity, Messages message) {
        if (message == Messages.LESSON_CODE_ALREADY_EXISTS) {
            return entity.getLessonCode();
        }
        return null;
    }

    public LessonEntity findByLessonCode(int code) {
        return findByField(code, lessonRepository :: findByLessonCode);
    }

    @CacheEvict(cacheNames = {"lesson", "allLesson"}, allEntries = true)
    public void setDepartment(int lessonCode, String departmentName) {
        LessonEntity lessonEntity = findByLessonCode(lessonCode);
        DepartmentEntity departmentEntity = departmentService.findByName(departmentName);
        lessonEntity.setDepartmentEntity(departmentEntity);
        super.save(lessonEntity);
    }
}