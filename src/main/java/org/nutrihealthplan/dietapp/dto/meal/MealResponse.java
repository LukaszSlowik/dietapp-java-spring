package org.nutrihealthplan.dietapp.dto.meal;

import java.time.Instant;
import java.util.List;

public class MealResponse {
    private Long mealId;
    private Instant mealDate;
    private List<MealProductResponse> products;
    private NutritionInfo nutrition;
    private String message;
}
