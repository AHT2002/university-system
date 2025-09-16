package com.example.university_system.service.impl;

import com.example.university_system.entity.*;
import com.example.university_system.enums.CourseGradeStatus;
import com.example.university_system.enums.Messages;
import com.example.university_system.exception.ConflictException;
import com.example.university_system.exception.NotFoundException;
import com.example.university_system.repository.CourseRepository;
import com.example.university_system.repository.StudentCourseGradeRepository;
import com.example.university_system.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService extends BaseService<CourseEntity, Long> {

    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final LessonService lessonService;
    private final StudentCourseGradeRepository studentCourseGradeRepository;

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
    protected Map<Messages, Function<Object, Optional<CourseEntity>>> getUniqueFieldCheckers() {
        Map<Messages, Function<Object, Optional<CourseEntity>>> checkers = new HashMap<>();
        checkers.put(Messages.COURSES_AVAILABLE_BY_CODE, code -> courseRepository.findByCode((int) code));
        return checkers;
    }

    @Override
    protected Object getFieldValue(CourseEntity entity, Messages message) {
        if (message == Messages.COURSES_AVAILABLE_BY_CODE) {
            return entity.getCode();
        }
        return null;
    }

    @Override
    protected void updateEntity(CourseEntity entity, CourseEntity existingEntity) {
        existingEntity.setSemester(entity.getSemester());
        if (entity.getLessonEntity() != null) {
            LessonEntity lesson = lessonService.findByLessonCode(entity.getLessonEntity().getLessonCode());
            existingEntity.setLessonEntity(lesson);
        }
        if (entity.getProfessorEntity() != null) {
            ProfessorEntity professor = professorService.findByCode(entity.getProfessorEntity().getCode());
            existingEntity.setProfessorEntity(professor);
        }
    }


    public CourseEntity findByCode(int code) {
        return findByField(code, courseRepository::findByCode);
    }

    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true, cacheResolver = "cacheResolver")
    public void addStudent(int codeCourse, long stdNumber) {
        StudentEntity studentEntity = studentService.findByStdNumber(stdNumber);
        CourseEntity courseEntity = findByCode(codeCourse);

        boolean alreadyEnrolled = studentCourseGradeRepository.findByStudentIdAndCourseId(studentEntity.getId(), courseEntity.getId()).isPresent();
        if (alreadyEnrolled) {
            throw new ConflictException("دانشجو قبلاً به این درس اضافه شده است.");
        }

        StudentCourseGradeEntity gradeEntity = new StudentCourseGradeEntity();
        gradeEntity.setStudent(studentEntity);
        gradeEntity.setCourse(courseEntity);
        gradeEntity.setStatus(CourseGradeStatus.IN_PROGRESS);
        gradeEntity.setGrade(null);
        studentCourseGradeRepository.save(gradeEntity);
    }

    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true, cacheResolver = "cacheResolver")
    public void removeStudent(int codeCourse, long stdNumber) {
        StudentEntity studentEntity = studentService.findByStdNumber(stdNumber);
        CourseEntity courseEntity = findByCode(codeCourse);

        boolean alreadyEnrolled = studentCourseGradeRepository.findByStudentIdAndCourseId(studentEntity.getId(), courseEntity.getId()).isPresent();
        if (!alreadyEnrolled) {
            throw new NotFoundException(Messages.STUDENT_DOES_NOT_HAVE_THE_COURSE.getDescription());
        }

        studentCourseGradeRepository.findByStudentIdAndCourseId(studentEntity.getId(), courseEntity.getId())
                .ifPresent(studentCourseGradeRepository::delete);
    }


    @Cacheable(cacheNames = "courseStudents", key = "#codeCourse", cacheResolver = "cacheResolver")
    public List<StudentEntity> listStudents(int codeCourse) {
        CourseEntity courseEntity = findByCode(codeCourse);

        List<StudentEntity> students = studentCourseGradeRepository.findByCourseId(courseEntity.getId())
                .stream()
                .map(StudentCourseGradeEntity::getStudent)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            throw new NotFoundException(Messages.COURSE_DOES_NOT_HAVE_ANY_STUDENT.getDescription());
        }

        return students;
    }


    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true, cacheResolver = "cacheResolver")
    public void assignProfessor(int codeCourse, int codeProfessor) {
        ProfessorEntity professorEntity = professorService.findByCode(codeProfessor);
        CourseEntity courseEntity = findByCode(codeCourse);

        if (courseEntity.getProfessorEntity() != null) {
            ProfessorEntity oldProfessor = courseEntity.getProfessorEntity();
            oldProfessor.getCourses().remove(courseEntity);
        }

        courseEntity.setProfessorEntity(professorEntity);
        super.save(courseEntity);
    }

    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true, cacheResolver = "cacheResolver")
    public void removeProfessor(int codeCourse) {
        CourseEntity courseEntity = findByCode(codeCourse);
        if (courseEntity.getProfessorEntity() == null) {
            throw new NotFoundException(Messages.COURSE_DOES_NOT_HAVE_PROFESSOR.getDescription());
        }

        courseEntity.setProfessorEntity(null);
        super.save(courseEntity);
    }

    @Cacheable(cacheNames = "courseProfessor", key = "#codeCourse", cacheResolver = "cacheResolver")
    public ProfessorEntity getProfessor(int codeCourse) {
        CourseEntity courseEntity = findByCode(codeCourse);
        if (courseEntity.getProfessorEntity() == null) {
            throw new NotFoundException(Messages.COURSE_DOES_NOT_HAVE_PROFESSOR.getDescription());
        }
        return courseEntity.getProfessorEntity();
    }

    @CacheEvict(cacheNames = {"course", "allCourse", "courseStudents", "courseProfessor"}, allEntries = true)
    public void setLesson(int codeCourse, int lessonCode) {
        CourseEntity courseEntity = findByCode(codeCourse);
        LessonEntity lessonEntity = lessonService.findByLessonCode(lessonCode);
        courseEntity.setLessonEntity(lessonEntity);
        super.save(courseEntity);
    }
}