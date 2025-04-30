package org.nutrihealthplan.dietapp.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.ResponseApiFactory;
import org.nutrihealthplan.dietapp.service.RefreshTokenService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthTokenFilter
        extends OncePerRequestFilter
{

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final RefreshTokenService refreshTokenService;
    private static final Set<String> PUBLIC_ENDPOINTS = Set.of(
            "/auth/signin",
            "/auth/register",
            "/auth/refresh-token",
            "/api/public/products"
            );

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {
        log.debug("AuthTokenFilter called for URI: {}", request.getRequestURI());

        String path = request.getRequestURI();
        if (PUBLIC_ENDPOINTS.contains(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = parseJwt(request);
        try {
            if (jwt == null || !jwtUtils.validateJwtToken(jwt)) {
                log.warn("No valid JWT token found in request");
                sendErrorResponse(response, "Invalid or missing JWT token", request.getRequestURI());
                return;
            }
            String username = jwtUtils.getUserNameFromJwtToken(jwt);
            authenticateUser(username, request);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            handleExpiredAccessToken(request, response, filterChain, path);
        } catch (JwtException | UsernameNotFoundException e) {
            log.warn("Authentication failed: {}", e.getMessage());
            log.debug("Stacktrace:", e);
            sendErrorResponse(response, "Invalid or expired JWT token", path);
        } catch (RuntimeException e) {
            log.error("Unexpected exception in AuthTokenFilter", e);
            throw e;
        }
    }

    private void handleExpiredAccessToken(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain filterChain,
                                          String path) throws IOException, ServletException {
        String refreshToken = extractRefreshTokenFromCookies(request);

        if (refreshToken == null || !jwtUtils.validateJwtToken(refreshToken)) {
            log.warn("Invalid or missing refresh token");
            sendErrorResponse(response, "Invalid or missing refresh token", path);
            return;
        }

        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        String storedRefreshToken = refreshTokenService.getRefreshToken(username);

        if (storedRefreshToken == null) {
            log.error("No refresh token found for user: {}", username);
            sendErrorResponse(response, "Invalid or missing refresh token", path);
            return;
        }

        if (!refreshToken.equals(storedRefreshToken)) {
            log.warn("Refresh token mismatch for user: {}", username);
            sendErrorResponse(response, "Refresh token mismatch", path);
            return;
        }

        String newAccessToken = jwtUtils.generateTokenFromUsername(username);
        if (newAccessToken == null) {
            log.error("Failed to generate new access token");
            sendErrorResponse(response, "Failed to generate new access token", path);
            return;
        }

        response.setHeader("Authorization", "Bearer " + newAccessToken);
        authenticateUser(username, request);
        log.debug("New access token generated and added to response header");

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromHeader(request);
        log.debug("Extracted JWT token: {}", jwt);
        return jwt;
    }

    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if ("refreshToken".equals(cookie.getName())) {
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                return cookie.getValue();
            }
        }
        return null;
    }

    private void authenticateUser(String username, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.debug("User authenticated successfully: {}", username);
        log.debug("Roles from JWT: {}", userDetails.getAuthorities());
    }

    private void sendErrorResponse(HttpServletResponse response, String message, String path) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        ResponseApi<Object> errorResponse = ResponseApiFactory.error("401", message, path);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        response.flushBuffer();
    }
}