package org.nutrihealthplan.dietapp.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.dto.LoginRequest;
import org.nutrihealthplan.dietapp.dto.LoginResponse;
import org.nutrihealthplan.dietapp.exceptions.AuthException;
import org.nutrihealthplan.dietapp.jwt.JwtUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            String jwtToken = jwtUtils.generateTokenFromUsername(username);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            String newRefreshToken = jwtUtils.generateRefreshTokenFromUsername(username);
            refreshTokenService.saveOrUpdateRefreshToken(username,newRefreshToken);
            return LoginResponse.builder()
                    .username(userDetails.getUsername())
                    .roles(roles)
                    .jwtToken(jwtToken)
                    .build();
        } catch (BadCredentialsException e) {
            throw new AuthException("Invalid username or password.");
        } catch (DisabledException e) {
            throw new AuthException("User account is disabled.");
        } catch (LockedException e) {
            throw new AuthException("User account is locked.");
        } catch (AuthenticationException e) {
            throw new AuthException("Authentication failed.");
        }

    }

    @Override
    public LoginResponse refreshToken(HttpServletRequest request) {
        String refreshToken = extractRefreshTokenFromCookies(request);
        if(refreshToken == null){
            throw new AuthException("Refresh token is missing");
        }
        if(!jwtUtils.validateJwtToken(refreshToken)){
            throw   new AuthException("Invalid refresh token");
        }
        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String newAccessToken = jwtUtils.generateTokenFromUsername(username);
        String newRefreshToken = jwtUtils.generateRefreshTokenFromUsername(username);
        refreshTokenService.saveOrUpdateRefreshToken(username,newRefreshToken);
        return LoginResponse.builder()
                .username(username)
                .roles(userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .jwtToken(newAccessToken)
                .build();
    }

    @Override
    public String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if(request.getCookies() != null){
            for(Cookie cookie: request.getCookies()){
                if("refreshToken".equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
