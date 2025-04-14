package org.nutrihealthplan.dietapp.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.ResponseApiFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ResponseApi<Object>> handleRecordNotFoundException(RecordNotFoundException ex,
                                                                              HttpServletRequest request) {
        ResponseApi<Object> response = ResponseApiFactory.error(
                "404",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(RecordNotCreatedException.class)
    public ResponseEntity<ResponseApi<Object>> handleRecordNotCreatedException(RecordNotCreatedException ex,
                                                                               HttpServletRequest request){
                log.error("Error creating record: {}",ex.getMessage(),ex);
                ResponseApi<Object> response = ResponseApiFactory.error(
                        "500",
                        ex.getMessage(),
                        request.getRequestURI()
                );
                return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);


    }
    @ExceptionHandler(AuthenticatedUserNotFoundException.class)
    public ResponseEntity<ResponseApi<Object>> handleAuthenticatedUserNotFound(AuthenticatedUserNotFoundException ex,
                                                                               HttpServletRequest request) {
        log.error("Authentication error: {}", ex.getMessage());
        ResponseApi<Object> response = ResponseApiFactory.error(
                "401",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
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
