package com.example.university_system.service.security;

import com.example.university_system.component.maper.BaseMapper;
import com.example.university_system.dto.security.authentication.request.AuthenticationRequestDto;
import com.example.university_system.dto.security.authentication.response.AuthenticationResponseDto;
import com.example.university_system.dto.user.AddUserDTO;
import com.example.university_system.dto.user.ViewUserDTO;
import com.example.university_system.entity.UserEntity;
import com.example.university_system.entity.security.AccessTokenEntity;
import com.example.university_system.entity.security.RefreshTokenEntity;
import com.example.university_system.repository.security.AccessTokenRepository;
import com.example.university_system.repository.security.RefreshTokenRepository;
import com.example.university_system.repository.user.BaseUserRepository;
import com.example.university_system.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService{
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public <U extends UserEntity, A extends AddUserDTO, V extends ViewUserDTO>
    AuthenticationResponseDto register(A requestDto, BaseMapper<U, A, V> mapper, BaseUserRepository<U> repository) {
        U user = mapper.toAddEntity(requestDto);
        U savedUser = repository.save(user);

        Instant issuedAt = Instant.now();
        String accessToken = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveAccessToken(savedUser, accessToken, issuedAt);
        saveRefreshToken(savedUser, refreshToken, issuedAt);

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getNationalCode(),
                        request.getPassword()
                )
        );
        var user = repository.findByNationalCode(request.getNationalCode())
                .orElseThrow();

        Instant issuedAt = Instant.now();
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokens(user);
        saveAccessToken(user, accessToken, issuedAt);
        saveRefreshToken(user, refreshToken, issuedAt);

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    private void saveAccessToken(UserEntity user, String token, Instant issuedAt) {
        var accessToken = AccessTokenEntity.builder()
                .user(user)
                .token(token)
                .issuedAt(issuedAt)
                .revoked(false)
                .build();
        accessTokenRepository.save(accessToken);
    }

    private void saveRefreshToken(UserEntity user, String token, Instant issuedAt) {
        var refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .token(token)
                .issuedAt(issuedAt)
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshToken);
    }


    private void revokeAllTokens(UserEntity user) {
        List<AccessTokenEntity> validAccessTokens = accessTokenRepository.findAllValidTokenByUser(user.getId());
        validAccessTokens.forEach(t -> t.setRevoked(true));
        accessTokenRepository.saveAll(validAccessTokens);

        List<RefreshTokenEntity> validRefreshTokens = refreshTokenRepository.findAllValidTokenByUser(user.getId());
        validRefreshTokens.forEach(t -> t.setRevoked(true));
        refreshTokenRepository.saveAll(validRefreshTokens);
    }


    @Transactional
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return;

        String refreshToken = authHeader.substring(7);
        String userNationalCode = jwtService.extractUsername(refreshToken);

        if (userNationalCode != null) {
            var user = userRepository.findByNationalCode(userNationalCode)
                    .orElseThrow();

            // بررسی اعتبار refresh token در دیتابیس
            var storedRefreshToken = refreshTokenRepository.findByToken(refreshToken)
                    .orElseThrow(() -> new RuntimeException("Refresh token not found"));

            if (jwtService.isTokenValid(refreshToken, user) && !storedRefreshToken.isRevoked()) {
                Instant issuedAt = Instant.now();
                var newAccessToken = jwtService.generateToken(user);
                var newRefreshToken = jwtService.generateRefreshToken(user);

                revokeAllTokens(user);
                saveAccessToken(user, newAccessToken, issuedAt);
                saveRefreshToken(user, newRefreshToken, issuedAt);

                var authResponse = AuthenticationResponseDto.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
