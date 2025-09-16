package com.example.university_system.config.caching;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffeineCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager(
                "student", "allStudent",
                "course", "allCourse", "courseStudents", "courseProfessor",
                "professor", "allProfessor", "department", "allDepartment",
                "lesson", "allLesson", "studentCourseGrades", "allStudentCourseGrades"
        );
        manager.setCaffeine(caffeineCacheBuilder());
        return manager;
    }

    @Bean
    public DynamicCacheResolver cacheResolver(CacheManager cacheManager) {
        return new DynamicCacheResolver(cacheManager);
    }

    public Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(512)
                .maximumSize(2048)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .weakKeys()
                .recordStats();
    }
}