package org.nutrihealthplan.dietapp.controller;

import org.nutrihealthplan.dietapp.dto.ChangePasswordRequest;
import org.nutrihealthplan.dietapp.model.UserEntity;

import org.nutrihealthplan.dietapp.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
//@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class PasswordController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordController(UserRepository userRepository,PasswordEncoder passwordEncoder )
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(request.email());
        UserEntity user = optionalUserEntity.get();
//        user.setPassword(passwordEncoder.encode(request.newPassword()));
//        UserEntity save = userRepository.save(user);

        return ResponseEntity.ok("Changed");

    }
    @GetMapping("/user-roles")
    public String getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        StringBuilder roles = new StringBuilder();

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roles.append(authority.getAuthority()).append(" ");
        }

        return "User roles: " + roles.toString();
    }

}
