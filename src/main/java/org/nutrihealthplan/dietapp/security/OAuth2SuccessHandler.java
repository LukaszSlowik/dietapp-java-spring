package org.nutrihealthplan.dietapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.jwt.JwtUtils;
import org.nutrihealthplan.dietapp.model.User;
import org.nutrihealthplan.dietapp.model.enums.AuthProvider;
import org.nutrihealthplan.dietapp.model.enums.Role;
import org.nutrihealthplan.dietapp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils; // Twój własny komponent do generowania tokena
    private final UserRepository userRepository;

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                Authentication authentication) throws IOException {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");

            User user = userRepository.findByEmail(email).orElseGet(() -> {
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setPassword("");
                newUser.setRole(Role.USER);
                //TODO Need to be dynamic
                newUser.setAuthProvider(AuthProvider.GOOGLE);
                return userRepository.save(newUser);
            });


            String accessToken = jwtUtils.generateTokenFromUsername(email);
            String refreshToken = jwtUtils.generateRefreshTokenFromUsername(email);


            Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            //TODO ustawic wedlug srodowiska
            refreshTokenCookie.setSecure(false);
            refreshTokenCookie.setPath("/auth/refresh-token");
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            response.addCookie(refreshTokenCookie);

            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), Map.of("accessToken", accessToken));
}}