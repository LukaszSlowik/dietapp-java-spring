package org.nutrihealthplan.dietapp.security;



import jakarta.annotation.Nullable;
import org.nutrihealthplan.dietapp.model.UserEntity;
import org.nutrihealthplan.dietapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
//@Slf4j
@Component
public class CustomAuditorAware implements AuditorAware<UserEntity> {
    private final UserRepository userRepository;

    public CustomAuditorAware(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private static final Logger log = LoggerFactory.getLogger(CustomAuditorAware.class);


    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        String principal = auth.getName();
        return userRepository.findByEmail(principal);




    }}

//    public Optional<UserEntity> getCurrentAuditor() {
//        return Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .flatMap(principal -> {
//                    if (principal instanceof UserPrincipal userPrincipal) {
//                        return Optional.of(userPrincipal.getUserEntity());
//                    }
//                    return Optional.empty();
//                });
//    }}



