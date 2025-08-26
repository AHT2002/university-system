package com.example.university_system.service.impl;

import com.example.university_system.enums.Messages;
import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.exception.ConflictException;
import com.example.university_system.exception.NotFoundException;
import com.example.university_system.repository.CourseRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService extends BaseService<CourseEntity, Long> {

    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final ProfessorService professorService;

    @Override
    protected CourseRepository getRepository() {
        return courseRepository;
    }

    @Override
    protected Messages getNotFoundMessage() {
        return Messages.COURSE_NOT_FOUND;
    }

    @Override
    public String getCacheName() {
        return "course";
    }

    @Override
    public String getAllCacheName() {
        return "allCourse";
    }

    @Override
    protected void updateEntity(CourseEntity entity, CourseEntity existingEntity) {
        existingEntity.setTitle(entity.getTitle());
        existingEntity.setUnits(entity.getUnits());
        // code تغییر نمی‌کنه
    }

    @Override
    public CourseEntity save(CourseEntity entity) {
        checkUniqueFields(entity);
        return super.save(entity);
    }

    private void checkUniqueFields(CourseEntity entity) {
        checkUniqueField(entity.getCode(), courseRepository::findByCode, Messages.COURSES_AVAILABLE_BY_CODE);
    }

    public CourseEntity findByCode(int code) {
        return findByField(code, courseRepository::findByCode);
    }

    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true, cacheResolver = "cacheResolver")
    public void addStudent(int codeCourse, long stdNumber) {
        StudentEntity studentEntity = studentService.findByStdNumber(stdNumber);
        CourseEntity courseEntity = findByCode(codeCourse);

        if (courseEntity.getStudentEntities().contains(studentEntity)) {
            throw new ConflictException("دانشجو قبلاً به این درس اضافه شده است.");
        }

        courseEntity.getStudentEntities().add(studentEntity);
        studentEntity.getCourses().add(courseEntity);

        studentService.update(studentEntity, studentEntity.getId());
        super.save(courseEntity);
    }

    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true, cacheResolver = "cacheResolver")
    public void removeStudent(int codeCourse, long stdNumber) {
        StudentEntity studentEntity = studentService.findByStdNumber(stdNumber);
        CourseEntity courseEntity = findByCode(codeCourse);

        if (!courseEntity.getStudentEntities().contains(studentEntity)) {
            throw new NotFoundException(Messages.STUDENT_DOES_NOT_HAVE_THE_COURSE.getDescription());
        }

        courseEntity.getStudentEntities().remove(studentEntity);
        studentEntity.getCourses().remove(courseEntity);

        studentService.update(studentEntity, studentEntity.getId());
        super.save(courseEntity);
    }

    @Cacheable(cacheNames = "courseStudents", key = "#codeCourse", cacheResolver = "cacheResolver")
    public List<StudentEntity> listStudents(int codeCourse) {
        return findByCode(codeCourse).getStudentEntities().stream().toList();
    }

    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true, cacheResolver = "cacheResolver")
    public void addProfessor(int codeCourse, int codeProfessor) {
        ProfessorEntity professorEntity = professorService.findByCode(codeProfessor);
        CourseEntity courseEntity = findByCode(codeCourse);

        if (courseEntity.getProfessorEntity() != null) {
            throw new ConflictException("این درس قبلاً یک استاد دارد.");
        }

        courseEntity.setProfessorEntity(professorEntity);
        professorEntity.getCourses().add(courseEntity);

        professorService.update(professorEntity, professorEntity.getId());
        super.save(courseEntity);
    }

    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true, cacheResolver = "cacheResolver")
    public void removeProfessor(int codeCourse) {
        CourseEntity courseEntity = findByCode(codeCourse);
        if (courseEntity.getProfessorEntity() == null) {
            throw new NotFoundException(Messages.PROFESSOR_DOES_NOT_HABE_THIS_COURSE.getDescription());
        }

        ProfessorEntity professorEntity = courseEntity.getProfessorEntity();
        courseEntity.setProfessorEntity(null);
        professorEntity.getCourses().remove(courseEntity);

        professorService.update(professorEntity, professorEntity.getId());
        super.save(courseEntity);
    }

    @Cacheable(cacheNames = "courseProfessor", key = "#codeCourse", cacheResolver = "cacheResolver")
    public ProfessorEntity getProfessor(int codeCourse) {
        CourseEntity courseEntity = findByCode(codeCourse);
        if (courseEntity.getProfessorEntity() == null) {
            throw new NotFoundException(Messages.PROFESSOR_DOES_NOT_HABE_THIS_COURSE.getDescription());
        }
        return courseEntity.getProfessorEntity();
    }
}