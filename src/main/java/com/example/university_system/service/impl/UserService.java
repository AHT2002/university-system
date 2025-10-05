package com.example.university_system.service.impl;

import com.example.university_system.dto.security.ChangePasswordRequestDto;
import com.example.university_system.entity.UserEntity;
import com.example.university_system.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserRepository userRepository;

    public void changePassword(ChangePasswordRequestDto request, Principal connectedUser) {
        var user = (UserEntity) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
    }


    public long findUserIdByNationalCode(String nationalCode) {
        return userRepository.findByNationalCode(nationalCode)
                .map(UserEntity::getId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with national code: " + nationalCode));
    }

    public UserEntity findByNationalCode(String nationalCode) {
        return userRepository.findByNationalCode(nationalCode)
                .orElseThrow(() -> new RuntimeException("User not found with national code: " + nationalCode));
    }
}