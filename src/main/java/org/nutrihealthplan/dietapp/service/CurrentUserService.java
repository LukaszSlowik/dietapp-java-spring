package org.nutrihealthplan.dietapp.service;

import org.nutrihealthplan.dietapp.model.UserEntity;

public interface CurrentUserService {
    UserEntity getCurrentUser();

    Long getCurrentUserId();
}
