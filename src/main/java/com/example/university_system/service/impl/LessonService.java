package com.example.university_system.service.impl;

import com.example.university_system.entity.DepartmentEntity;
import com.example.university_system.entity.LessonEntity;
import com.example.university_system.enums.Messages;
import com.example.university_system.exception.ConflictException;
import com.example.university_system.exception.NotFoundException;
import com.example.university_system.repository.LessonRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class LessonService extends BaseService<LessonEntity, Long> {

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
    protected void updateEntity(LessonEntity entity, LessonEntity existingEntity) {
        existingEntity.setTitle(entity.getTitle());
        existingEntity.setUnits(entity.getUnits());

        if (entity.getDepartmentEntity() != null) {
            DepartmentEntity department = departmentService.findByName(entity.getDepartmentEntity().getName());
            existingEntity.setDepartmentEntity(department);
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