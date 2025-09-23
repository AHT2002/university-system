package com.example.university_system.service.impl;

import com.example.university_system.component.CheckRequestsInputStringParameter;
import com.example.university_system.dto.lecture.UpdateLectureDTO;
import com.example.university_system.entity.*;
import com.example.university_system.enums.Messages;
import com.example.university_system.repository.LectureRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class LectureService extends BaseService<LectureEntity, Long, UpdateLectureDTO> {
    private final LectureRepository lectureRepository;
    private final CheckRequestsInputStringParameter checkString;
    private final ClassroomService classroomService;

    @Override
    protected LectureRepository getRepository() {
        return lectureRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.LECTURE_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "lecture";
    }

    @Override
    public String getAllCacheName() {
        return "allLecture";
    }


    @Override
    protected Map<Messages, Function<Object, Optional<LectureEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<LectureEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.LECTURE_AVAILABLE_BY_CODE, code -> lectureRepository.findByCode((String) code));
        return checkers;
    }

    @Override
    protected Object getFieldValue(LectureEntity entity, Messages message) {
        if (message == Messages.LECTURE_AVAILABLE_BY_CODE) {
            return entity.getCode();
        }
        return null;
    }

    @Override
    protected void updateEntity(UpdateLectureDTO dto, LectureEntity existingEntity) {
        if (dto.getDayOfWeek() != null) existingEntity.setDayOfWeek(dto.getDayOfWeek());
        if (dto.getClassTime() != null) existingEntity.setClassTime(dto.getClassTime());
        if (checkString.checkRequestsInputStringParameter(dto.getClassroomCode())) {
            ClassroomEntity classroom = classroomService.findByCode(dto.getClassroomCode());
            existingEntity.setClassroom(classroom);
        }
    }
}
