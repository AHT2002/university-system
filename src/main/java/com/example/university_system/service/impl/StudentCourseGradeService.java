package com.example.university_system.service.impl;

import com.example.university_system.entity.StudentCourseGradeEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.enums.CourseGradeStatus;
import com.example.university_system.enums.Messages;
import com.example.university_system.exception.NotFoundException;
import com.example.university_system.repository.StudentCourseGradeRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class StudentCourseGradeService extends BaseService<StudentCourseGradeEntity, Long> {

    private final StudentCourseGradeRepository gradeRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    @Override
    protected StudentCourseGradeRepository getRepository() {
        return gradeRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.GRADE_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "studentCourseGrades";
    }

    @Override
    public String getAllCacheName() {
        return "allStudentCourseGrades";
    }

    @Override
    protected Map<Messages, Function<Object, Optional<StudentCourseGradeEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<StudentCourseGradeEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.DUPLICATE_GRADE, value -> {
            if (value instanceof Long[]) {
                Long[] ids = (Long[]) value;
                return gradeRepository.findByStudentIdAndCourseId(ids[0], ids[1]);
            }
            return Optional.empty();
        });
        return checkers;
    }

    @Override
    protected Object getFieldValue(StudentCourseGradeEntity entity, Messages message) {
        if (message == Messages.DUPLICATE_GRADE) {
            return new Long[]{entity.getStudent().getId(), entity.getCourse().getId()};
        }
        return null;
    }

    @Override
    protected void updateEntity(StudentCourseGradeEntity entity, StudentCourseGradeEntity existingEntity) {
        if (entity.getGrade() != null) {
            existingEntity.setGrade(entity.getGrade());
        }
        existingEntity.setStatus(entity.getStatus());
    }

    @CacheEvict(cacheNames = {"studentCourseGrades", "allStudentCourseGrades"}, allEntries = true)
    public StudentCourseGradeEntity addGrade(Long studentId, Long courseId, Float grade, CourseGradeStatus status) {
        StudentEntity student = studentService.findById(studentId);
        CourseEntity course = courseService.findById(courseId);

        if (grade != null && (grade < 0 || grade > 20)) {
            throw new IllegalArgumentException("نمره باید بین 0 تا 20 باشد");
        }

        StudentCourseGradeEntity gradeEntry = new StudentCourseGradeEntity();
        gradeEntry.setStudent(student);
        gradeEntry.setCourse(course);
        gradeEntry.setGrade(grade);
        gradeEntry.setStatus(status);
        return save(gradeEntry);
    }

    @CacheEvict(cacheNames = {"studentCourseGrades", "allStudentCourseGrades"}, allEntries = true)
    public StudentCourseGradeEntity updateGrade(Long studentId, Long courseId, Float grade, CourseGradeStatus status) {
        StudentCourseGradeEntity existingGrade = findByField(
                new Long[]{studentId, courseId},
                value -> gradeRepository.findByStudentIdAndCourseId((Long) value[0], (Long) value[1])
        );
        if (grade != null && (grade < 0 || grade > 20)) {
            throw new IllegalArgumentException("نمره باید بین 0 تا 20 باشد");
        }
        existingGrade.setGrade(grade);
        existingGrade.setStatus(status);
        return save(existingGrade);
    }

    @CacheEvict(cacheNames = {"studentCourseGrades", "allStudentCourseGrades"}, allEntries = true, cacheResolver = "cacheResolver")
    public void deleteGrade(Long studentId, Long courseId) {
        StudentCourseGradeEntity gradeEntry = gradeRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new NotFoundException("نمره برای این جفت (studentId, courseId) یافت نشد"));
        deleteById(gradeEntry.getId());
    }
}