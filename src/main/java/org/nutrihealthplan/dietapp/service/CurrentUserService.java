package org.nutrihealthplan.dietapp.service;

import org.nutrihealthplan.dietapp.model.User;

public interface CurrentUserService {
    User getCurrentUser();

    Long getCurrentUserId();
}
