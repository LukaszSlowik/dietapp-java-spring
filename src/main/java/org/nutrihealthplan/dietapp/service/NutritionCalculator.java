package org.nutrihealthplan.dietapp.service;

import org.nutrihealthplan.dietapp.dto.meal.NutritionInfo;
import org.nutrihealthplan.dietapp.model.MealProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NutritionCalculator {
    NutritionInfo calculateTotalNutrition(List<MealProductEntity> entities);
    NutritionInfo calculateNutrition (MealProductEntity entity);

}
