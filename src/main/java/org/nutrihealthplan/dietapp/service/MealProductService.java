package org.nutrihealthplan.dietapp.service;

import org.nutrihealthplan.dietapp.dto.meal.MealCreateRequest;
import org.nutrihealthplan.dietapp.dto.meal.MealProductCreateResponse;
import org.nutrihealthplan.dietapp.model.User;

public interface MealProductService {
    MealProductCreateResponse createMeal(MealCreateRequest request);
    User resolveOwner(Long ownerIdOptional);
}
