package com.example.university_system.service.security;

import com.example.university_system.repository.security.AccessTokenRepository;
import com.example.university_system.repository.security.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final AccessTokenRepository accessTokenRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        final String jwt = authHeader.substring(7);

        // پیدا کردن توکن دسترسی
        var accessTokenOpt = accessTokenRepository.findByToken(jwt);
        if (accessTokenOpt.isPresent()) {
            var accessToken = accessTokenOpt.get();
            Long userId = accessToken.getUser().getId();

            // باطل کردن همه توکن‌های دسترسی کاربر
            var allAccessTokens = accessTokenRepository.findAllValidTokenByUser(userId);
            allAccessTokens.forEach(token -> token.setRevoked(true));
            accessTokenRepository.saveAll(allAccessTokens);

            // باطل کردن همه رفرش توکن‌های کاربر
            var allRefreshTokens = refreshTokenRepository.findAllValidTokenByUser(userId);
            allRefreshTokens.forEach(token -> token.setRevoked(true));
            refreshTokenRepository.saveAll(allRefreshTokens);
        }

        SecurityContextHolder.clearContext();
    }
}

