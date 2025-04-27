package org.nutrihealthplan.dietapp.service;

import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.dto.RegisterRequest;
import org.nutrihealthplan.dietapp.exceptions.AuthException;
import org.nutrihealthplan.dietapp.model.UserEntity;
import org.nutrihealthplan.dietapp.model.enums.Role;
import org.nutrihealthplan.dietapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new AuthException("User already exists with email: " + registerRequest.getEmail());
        }
        UserEntity newUser = new UserEntity();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(Role.USER);
        userRepository.save(newUser);
    }
}
