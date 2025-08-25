package com.example.university_system.entity;


import com.example.university_system.component.validators.NationalIdValidator;
import com.example.university_system.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String family;

    @Column(unique = true, updatable = false)
    private String nationalCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date birthDay;

    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;


//    @Transient
//    private transient NationalIdValidator nationalIdValidator;
//
//    public void setNationalId(String nationalId) {
//        nationalIdValidator.validateNationalId(nationalId);
//        this.nationalCode = nationalId;
//    }
}
