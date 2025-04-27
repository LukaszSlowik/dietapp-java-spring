package org.nutrihealthplan.dietapp.dto.meal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nutrihealthplan.dietapp.model.enums.UnitType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class MealProductsCreated {
    private UUID mealId;
    private LocalDate mealDate;
    private Long productId;
    private String productName;
    private UnitType unitType;
    private BigDecimal amount;
    private BigDecimal grams;
    private NutritionInfo nutrition;
}
