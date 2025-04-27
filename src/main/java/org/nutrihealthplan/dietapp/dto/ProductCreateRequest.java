package org.nutrihealthplan.dietapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.nutrihealthplan.dietapp.model.enums.UnitType;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Kcal per 100g cannot be null")
    private BigDecimal kcalPer100g;
    @NotNull(message = "Protein per 100g cannot be null")
    private BigDecimal proteinPer100g;
    @NotNull(message = "Carbs per 100g cannot be null")
    private BigDecimal carbsPer100g;
    @NotNull(message = "Fat per 100g cannot be null")
    private BigDecimal fatPer100g;
    private List<UnitInfo> units;
    private Scope scope;


}
