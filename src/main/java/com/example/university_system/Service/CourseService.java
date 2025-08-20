package com.example.university_system.Service;


import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.exception.ConflictException;
import com.example.university_system.exception.NotFoundException;
import com.example.university_system.repository.CourseRepository;
import org.springframework.cache.annotation.Cacheable;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final ProfessorService professorService;

    public CourseEntity save(CourseEntity courseEntity) {
        if (courseRepository.findByCode(courseEntity.getCode()).isPresent())
            throw new ConflictException("The course with the desired code is available in the system.");
        return courseRepository.save(courseEntity);
    }

    public CourseEntity update(CourseEntity courseEntityUpdate) {
        CourseEntity courseEntity = findById(courseEntityUpdate.getId());
        courseEntityUpdate.setCode(courseEntity.getCode());
        return courseRepository.save(courseEntityUpdate);
    }

    @Cacheable(value = "course", key = "#id")
    public CourseEntity findById(Long id) {
        Optional<CourseEntity> optional = courseRepository.findById(id);
        if (optional.isEmpty())
            throw new NotFoundException("Course Not found.");
        return optional.get();
    }

    @Cacheable(value = "course", key = "result.id")
    public CourseEntity findByCode(int code) {
        Optional<CourseEntity> optional = courseRepository.findByCode(code);
        if (optional.isEmpty())
            throw new NotFoundException("Course Not found.");
        return optional.get();
    }

    @Caching(evict = {
            @CacheEvict(value = "allCourse", allEntries = true),
            @CacheEvict(value = "course", key = "#id")
    })
    public void deleteById(Long id) {
        findById(id);
        courseRepository.deleteById(id);
    }

    @Cacheable(value = "allCourse")
    public List<CourseEntity> findAll() {
        return courseRepository.findAll();
    }

    @Caching(evict = {
            @CacheEvict(value = "allCourse", allEntries = true),
            @CacheEvict(value = "course", allEntries = true)
    })
    public void deleteAll() {
        courseRepository.deleteAll();
    }


    public void addStudent(int codeCourse, long stdNumber) {
        StudentEntity studentEntity = studentService.findByStdNumber(stdNumber);
        CourseEntity courseEntity = findByCode(codeCourse);

        courseEntity.getStudentEntities().add(studentEntity);
        studentEntity.getCourses().add(courseEntity);

        studentService.update(studentEntity);
        update(courseEntity);
    }

    public void removeStudent(int codeCourse, long stdNumber) {
        StudentEntity studentEntity = studentService.findByStdNumber(stdNumber);
        CourseEntity courseEntity = findByCode(codeCourse);

        if (!courseEntity.getStudentEntities().contains(studentEntity))
            throw new NotFoundException("The student does not have this course.");
        courseEntity.getStudentEntities().remove(studentEntity);
        studentEntity.getCourses().remove(courseEntity);

        studentService.update(studentEntity);
        update(courseEntity);
    }

    public List<StudentEntity> listStudents(int codeCourse) {
        return findByCode(codeCourse).getStudentEntities().stream().toList();
    }

    public void addProfessor(int codeCourse, int codeProfessor) {
        ProfessorEntity professorEntity = professorService.findByCode(codeProfessor);
        CourseEntity courseEntity = findByCode(codeCourse);

        courseEntity.setProfessorEntity(professorEntity);
        professorEntity.getCourses().add(courseEntity);

        professorService.update(professorEntity);
        update(courseEntity);
    }

    public void removeProfessor(int codeCourse) {
        CourseEntity courseEntity = findByCode(codeCourse);
        if (courseEntity.getProfessorEntity() == null)
            throw new NotFoundException("The professor is not set for this course.");

        ProfessorEntity professorEntity = courseEntity.getProfessorEntity();
        courseEntity.setProfessorEntity(null);

        professorEntity.getCourses().remove(courseEntity);

        professorService.update(professorEntity);
        update(courseEntity);
    }

    public ProfessorEntity getProfessor(int codeCourse) {
        CourseEntity courseEntity = findByCode(codeCourse);
        if (courseEntity.getProfessorEntity() == null)
            throw new NotFoundException("The professor is not set for this course.");
        return courseEntity.getProfessorEntity();
    }
}
