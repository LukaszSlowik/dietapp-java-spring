package org.nutrihealthplan.dietapp.service;

import lombok.AllArgsConstructor;
import org.nutrihealthplan.dietapp.model.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CurrentUserServiceImpl implements  CurrentUserService{

    private final AuditorAware<UserEntity> auditorAware;

    @Override
    public UserEntity getCurrentUser() {
        return auditorAware.getCurrentAuditor()
                .orElseThrow(() -> new IllegalStateException("No authenticated user found"));
    }

    @Override
    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
