package com.example.university_system.component.aspects;

import com.example.university_system.component.validators.NationalIdValidator;
import com.example.university_system.entity.ProfessorEntity;
import com.example.university_system.entity.StudentEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class NationalIdValidationAspect {

    @Autowired
    private NationalIdValidator nationalIdValidator;

    @Before("execution(* com.example.university_system.service..*Service.save(..))")
    public void validateNationalIdBeforeSave(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("Aspect triggered for method: " + joinPoint.getSignature().toShortString());
        System.out.println("Args: " + Arrays.toString(args));

        if (args.length > 0) {
            if (args[0] instanceof StudentEntity studentEntity) {
                System.out.println("Validating Student National Code: " + studentEntity.getNationalCode());
                nationalIdValidator.validateNationalId(studentEntity.getNationalCode());
            } else if (args[0] instanceof ProfessorEntity professorEntity) {
                System.out.println("Validating Professor National Code: " + professorEntity.getNationalCode());
                nationalIdValidator.validateNationalId(professorEntity.getNationalCode());
            } else {
                System.out.println("No matching entity for validation: " + (args[0] != null ? args[0].getClass().getName() : "null"));
            }
        } else {
            System.out.println("No arguments provided to save method");
        }
    }
}