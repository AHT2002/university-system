package com.example.university_system.Service;


import com.example.university_system.entity.CourseEntity;
import com.example.university_system.entity.StudentEntity;
import com.example.university_system.exception.ConflictException;
import com.example.university_system.exception.NotFoundException;
import com.example.university_system.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentEntity save(StudentEntity studentEntity) {
        Optional<StudentEntity> optional;

        optional = studentRepository.findByNationalCode(studentEntity.getNationalCode());
        if (optional.isPresent()) {
            throw new ConflictException("The student with the desired National Code is available in the system.");
        }

        optional = studentRepository.findByUsername(studentEntity.getUsername());
        if (optional.isPresent())
            throw new ConflictException("The student with the desired username is available in the system.");

        optional = studentRepository.findByStdNumber(studentEntity.getStdNumber());
        if (optional.isPresent())
            throw new ConflictException("The student with the desired StdNumber is available in the system.");

        return studentRepository.save(studentEntity);
    }


    public StudentEntity update(StudentEntity studentEntityUpdate) {
        StudentEntity studentEntity = findById(studentEntityUpdate.getId());

        studentEntityUpdate.setNationalCode(studentEntity.getNationalCode());
        studentEntityUpdate.setUsername(studentEntity.getUsername());
        studentEntityUpdate.setStdNumber(studentEntity.getStdNumber());

        return studentRepository.save(studentEntityUpdate);
    }

    public void deleteById(Long id) {
        findById(id);
        studentRepository.deleteById(id);
    }

    public StudentEntity findById(Long id) {
        Optional<StudentEntity> optional = studentRepository.findById(id);
        if (optional.isEmpty())
            throw new NotFoundException("Student Not found.");
        return optional.get();
    }

    public StudentEntity findByStdNumber(Long stdNumber) {
        Optional<StudentEntity> optional = studentRepository.findByStdNumber(stdNumber);
        if (optional.isEmpty())
            throw new NotFoundException("Student Not found.");
        return optional.get();
    }

    public List<StudentEntity> findAll() {
        return studentRepository.findAll();
    }

    public void deleteAll() {
        studentRepository.deleteAll();
    }

    public List<CourseEntity> listCoursesStudent(long stdNumber) {
        StudentEntity studentEntity = findByStdNumber(stdNumber);
        return studentEntity.getCourses().stream().toList();
    }

}
