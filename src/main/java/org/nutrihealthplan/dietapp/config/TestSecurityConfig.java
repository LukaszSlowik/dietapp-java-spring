package org.nutrihealthplan.dietapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.nutrihealthplan.dietapp.jwt.AuthTokenFilter;
import org.nutrihealthplan.dietapp.jwt.JwtUtils;
import org.nutrihealthplan.dietapp.service.RefreshTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


//@Configuration
//@EnableWebSecurity
//@AllArgsConstructor
public class TestSecurityConfig {

//
//    private final JwtUtils jwtUtils;
//    private final UserDetailsService userDetailsService;
//    private final ObjectMapper objectMapper;
//    private final RefreshTokenService refreshTokenService;
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((requests) ->
//                requests.anyRequest().permitAll());
//        http.csrf(AbstractHttpConfigurer::disable);
//        return http.build();
//    }
}