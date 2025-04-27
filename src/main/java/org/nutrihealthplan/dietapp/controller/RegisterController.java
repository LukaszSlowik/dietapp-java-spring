package org.nutrihealthplan.dietapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.dto.RegisterRequest;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.ResponseApiFactory;
import org.nutrihealthplan.dietapp.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    public final RegistrationService registrationService;
    @PostMapping("/register")
    public ResponseEntity<ResponseApi<String>> registerUser(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request){
        String path = request.getServletPath();
        registrationService.register(registerRequest);
        return ResponseEntity.ok(ResponseApiFactory.success("User registered successfully",path));
    }
}
