package org.nutrihealthplan.dietapp.dto.meal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MealProductCreateResponse {
    private UUID mealId;
    private Integer mealNumber;
    private LocalDate mealDate;
    private List<MealProductsCreated> products;
    private NutritionInfo nutrition;
    private String message;
    private Long ownerId;

}
