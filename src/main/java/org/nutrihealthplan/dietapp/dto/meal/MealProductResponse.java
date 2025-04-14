package org.nutrihealthplan.dietapp.dto.meal;


import org.nutrihealthplan.dietapp.model.enums.UnitType;

import java.math.BigDecimal;

public class MealProductResponse {
    private Long productId;
    private String name;
    private UnitType unitType;
    private BigDecimal amount;
    private BigDecimal grams;
    private NutritionInfo nutritionInfo;
}
