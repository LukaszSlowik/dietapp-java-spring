package org.nutrihealthplan.dietapp.exceptions;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.ResponseApiFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.nutrihealthplan.dietapp.utils.ErrorHandlingHelper.extractRelevantMessage;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ResponseApi<Object>> handleRecordNotFoundException(RecordNotFoundException ex,
                                                                             HttpServletRequest request) {
        log.error("Record not found: {}", ex.getMessage(), ex);  // Log error for missing record
        ResponseApi<Object> response = ResponseApiFactory.error(
                "404",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RecordNotCreatedException.class)
    public ResponseEntity<ResponseApi<Object>> handleRecordNotCreatedException(RecordNotCreatedException ex,
                                                                               HttpServletRequest request) {
        log.error("Error creating record: {}", ex.getMessage(), ex);  // Log error when record creation fails
        ResponseApi<Object> response = ResponseApiFactory.error(
                "500",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<ResponseApi<Object>> handleJwtException(JwtException ex, HttpServletRequest request) {
//        log.error("JWT Authentication failed: {}", ex.getMessage());
//        ResponseApi<Object> response = ResponseApiFactory.error(
//                "401",
//                "Invalid JWT token.",
//                request.getRequestURI()
//        );
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//    }

    @ExceptionHandler(AuthenticatedUserNotFoundException.class)
    public ResponseEntity<ResponseApi<Object>> handleAuthenticatedUserNotFound(AuthenticatedUserNotFoundException ex,
                                                                               HttpServletRequest request) {
        log.error("Authentication error: {}", ex.getMessage());  // Log error when authentication fails
        ResponseApi<Object> response = ResponseApiFactory.error(
                "401",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseApi<Object>> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("Illegal argument: {}", ex.getMessage());  // Log warning for illegal arguments
        ResponseApi<Object> response = ResponseApiFactory.error(
                "400",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseApi<Object>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        log.warn("Validation failed: {}", errors);  // Log validation errors as warnings
        ResponseApi<Object> response = ResponseApiFactory.error(
                "400",
                "VALIDATION_FAILED: " + errors.toString(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseApi<Object>> handleJsonParseError(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String errorMessage = "Invalid request format: " + extractRelevantMessage(ex.getMessage());
        log.error("Invalid JSON request: {}", errorMessage);  // Log error for invalid JSON format
        ResponseApi<Object> response = ResponseApiFactory.error(
                "400",
                "INVALID_JSON: " + errorMessage,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ResponseApi<Object>> handleAuthenticationCredentialsNotFoundException(
            AuthenticationCredentialsNotFoundException ex, HttpServletRequest request) {

        log.error("Authentication credentials not found: {}", ex.getMessage());  // Log error for missing credentials

        ResponseApi<Object> response = ResponseApiFactory.error(
                "401",
                "Authentication failed. Credentials not found.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ResponseApi<Object>> handleAuthException(AuthException ex,
                                                                             HttpServletRequest request) {
        log.warn("Authentication failed: {}", ex.getMessage());
        ResponseApi<Object> response = ResponseApiFactory.error(
                "401",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseApi<Object>> handleDatabaseError(DataIntegrityViolationException ex,
                                                                   HttpServletRequest request) {
        log.error("Database constraint error: {}", ex.getMessage());
        ResponseApi<Object> response = ResponseApiFactory.error(
                "409",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi<Object>> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error occurred.", ex);
        ResponseApi<Object> response = ResponseApiFactory.error(
                "500",
                "An unexpected error occurred.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
