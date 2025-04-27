package org.nutrihealthplan.dietapp.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@AllArgsConstructor
@Data
public class NutritionInfo {
    private BigDecimal kcal;
    private BigDecimal carbs;
    private BigDecimal fat;
    private BigDecimal protein;
}
