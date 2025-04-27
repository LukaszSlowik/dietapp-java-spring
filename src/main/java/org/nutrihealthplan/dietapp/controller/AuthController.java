package org.nutrihealthplan.dietapp.controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrihealthplan.dietapp.dto.LoginRequest;
import org.nutrihealthplan.dietapp.dto.LoginResponse;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.ResponseApiFactory;
import org.nutrihealthplan.dietapp.service.AuthService;
import org.nutrihealthplan.dietapp.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Slf4j

public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @Value("${spring.app.refreshTokenExpirationMs}")
    private int refreshTokenExpirationMs;

    @PostMapping("/signin")
    public ResponseEntity<ResponseApi<LoginResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                                       HttpServletRequest servletRequest,HttpServletResponse response
    ) {
        String path = servletRequest.getServletPath();
        LoginResponse loginResponse = authService.authenticate(loginRequest);
        String username = loginResponse.getUsername();
        String newRefreshToken = refreshTokenService.getRefreshToken(username);
        setRefreshTokenCookie(newRefreshToken,response);

        return ResponseEntity.ok(ResponseApiFactory.success(loginResponse, path));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseApi<LoginResponse>> refreshToken(HttpServletRequest servletRequest, HttpServletResponse response){
        String path = servletRequest.getServletPath();
        LoginResponse loginResponse = authService.refreshToken(servletRequest);
        String username = loginResponse.getUsername();
        String newRefreshToken = refreshTokenService.getRefreshToken(username);
        setRefreshTokenCookie(newRefreshToken,response);
        return ResponseEntity.ok(ResponseApiFactory.success(loginResponse,path));
    }
    private void setRefreshTokenCookie(String refreshToken, HttpServletResponse response){
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken",refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMillis(refreshTokenExpirationMs))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
    }

}
