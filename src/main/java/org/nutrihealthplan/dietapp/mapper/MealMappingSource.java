package org.nutrihealthplan.dietapp.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nutrihealthplan.dietapp.model.MealProductEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MealMappingSource {
    private UUID mealId;
    private LocalDate mealDate;
    private List<MealProductEntity> products;
    private Integer mealNumber;
    private Long ownerId;

}
