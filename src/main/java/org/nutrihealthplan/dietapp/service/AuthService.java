package org.nutrihealthplan.dietapp.service;

import jakarta.servlet.http.HttpServletRequest;
import org.nutrihealthplan.dietapp.dto.LoginRequest;
import org.nutrihealthplan.dietapp.dto.LoginResponse;

public interface AuthService {
    LoginResponse authenticate(LoginRequest loginRequest);
    LoginResponse refreshToken(HttpServletRequest request);
    String extractRefreshTokenFromCookies(HttpServletRequest request);
}
