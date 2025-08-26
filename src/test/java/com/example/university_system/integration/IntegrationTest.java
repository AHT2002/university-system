//package com.example.university_system.integration;
//
//
// <<<<<<< refactor/service-layer-refactor
//import com.example.university_system.service.impl.CourseService;
//import com.example.university_system.Service.StudentService;
// =======
//import com.example.university_system.service.CourseService;
//import com.example.university_system.service.StudentService;
// >>>>>>> main
//import com.example.university_system.dto.course.AddCourseDTO;
//import com.example.university_system.entity.CourseEntity;
//import com.example.university_system.entity.StudentEntity;
//import com.example.university_system.enums.AcademicLevel;
//import com.example.university_system.enums.Gender;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Date;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(locations = {"classpath:application-test.properties"})
//@AutoConfigureMockMvc
//public class IntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private CourseService courseService;
//    @Autowired
//    private StudentService studentService;
//
//    @BeforeEach
//    void setUp(){
//        courseService.deleteAll();
//        studentService.deleteAll();
//    }
//
//
//    @Test
//    void givenValidData_whenSaveCourse_thenIsCreatedShouldBeReturn() throws Exception {
//        // Given
//        AddCourseDTO addCourseDTO = new AddCourseDTO();
//        addCourseDTO.setCode(4);
//        addCourseDTO.setTitle("course4");
//        addCourseDTO.setUnits(3);
//
//        // When
//        mockMvc.perform(post("/course/v1/save")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(addCourseDTO)))
//                // Then
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.code").value(addCourseDTO.getCode()))
//                .andExpect(jsonPath("$.title").value(addCourseDTO.getTitle()))
//                .andExpect(jsonPath("$.units").value(addCourseDTO.getUnits()));
//    }
//
//    @Test
//    void givenValidData_whenFindAllCourse_thenAllCourseShouldBeReturn() throws Exception {
//        // Given
//        CourseEntity courseEntity1 = new CourseEntity();
//        courseEntity1.setCode(4);
//        courseEntity1.setTitle("course4");
//        courseEntity1.setUnits(3);
//
//        CourseEntity courseEntity2 = new CourseEntity();
//        courseEntity2.setCode(5);
//        courseEntity2.setTitle("course5");
//        courseEntity2.setUnits(3);
//
//        courseService.save(courseEntity1);
//        courseService.save(courseEntity2);
//
//        // When
//        mockMvc.perform(get("/course/v1/list"))
//                // Then
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2));
//    }
//
//    @Test
//    void givenValidData_whenDeleteCourse_thenNoContentShouldBeReturn() throws Exception {
//        // Given
//        CourseEntity courseEntity1 = new CourseEntity();
//        courseEntity1.setCode(4);
//        courseEntity1.setTitle("course4");
//        courseEntity1.setUnits(3);
//        courseEntity1 = courseService.save(courseEntity1);
//
//        // When
//        mockMvc.perform(delete("/course/v1/delete/{id}", courseEntity1.getId()))
//                // Then
//                .andExpect(status().isNoContent());
//
//        // When
//        mockMvc.perform(get("/course/v1/find/{id}", courseEntity1.getId()))
//                // Then
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Course Not found."));
//    }
//
////    @Test
////    void givenValidData_whenUpdateCourse_thenOkShouldBeReturn() throws Exception {
////        // Given
////        Course course1 = new Course();
////        course1.setCode(4);
////        course1.setTitle("course4");
////        course1.setUnits(3);
////        course1 = courseService.save(course1);
////
////        UpdateCourseDTO updateCourseDTO = new UpdateCourseDTO();
////        updateCourseDTO.setId(course1.getId());
////        updateCourseDTO.setTitle("course44");
////        updateCourseDTO.setUnits(2);
////
////        // When
////        mockMvc.perform(put("/course/v1/update", course1.getId())
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(updateCourseDTO)))
////                // Then
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.title").value(updateCourseDTO.getTitle()))
////                .andExpect(jsonPath("$.units").value(updateCourseDTO.getUnits()));
////    }
//
//    @Test
//    void givenValidData_whenFindByIdCourse_thenCourseShouldBeReturn() throws Exception {
//        // Given
//        CourseEntity courseEntity = new CourseEntity();
//        courseEntity.setCode(4);
//        courseEntity.setTitle("course4");
//        courseEntity.setUnits(3);
//        courseEntity = courseService.save(courseEntity);
//
//        // When
//        mockMvc.perform(get("/course/v1/find/{id}", courseEntity.getId()))
//                // Then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(courseEntity.getId()))
//                .andExpect(jsonPath("$.code").value(courseEntity.getCode()))
//                .andExpect(jsonPath("$.title").value(courseEntity.getTitle()))
//                .andExpect(jsonPath("$.units").value(courseEntity.getUnits()));
//    }
//
//    @Test
//    void givenValidData_whenListStudents_thenAllStudentShouldBeReturn() throws Exception {
//        // Given
//        CourseEntity courseEntity = new CourseEntity();
//        courseEntity.setCode(4);
//        courseEntity.setTitle("course4");
//        courseEntity.setUnits(3);
//        courseEntity = courseService.save(courseEntity);
//
//        StudentEntity studentEntity = new StudentEntity();
//        studentEntity.setName("name1");
//        studentEntity.setFamily("family1");
//        studentEntity.setBirthDay(new Date());
//        studentEntity.setGender(Gender.FEMALE);
//        studentEntity.setStdNumber(1234567890L);
//        studentEntity.setAcademicLevel(AcademicLevel.PHD);
//        studentEntity.setNationalCode(9876543210L);
//        studentEntity.setUsername("username1");
//        studentEntity.setPassword("password1");
//        studentService.save(studentEntity);
//
//        courseEntity.getStudentEntities().add(studentEntity);
//        courseService.update(courseEntity);
//
//        // When
//        mockMvc.perform(get("/course/v1/{codeCourse}/students", courseEntity.getCode()))
//                // Then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1));
//    }
//}
