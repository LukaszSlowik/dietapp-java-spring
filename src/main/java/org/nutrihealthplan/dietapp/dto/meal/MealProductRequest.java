package org.nutrihealthplan.dietapp.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nutrihealthplan.dietapp.model.enums.UnitType;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealProductRequest {
    private Long productId;
    private Instant mealDate;
    private UnitType unitType;
    BigDecimal amount;
}
