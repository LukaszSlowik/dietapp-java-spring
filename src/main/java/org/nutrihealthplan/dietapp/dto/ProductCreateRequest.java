package org.nutrihealthplan.dietapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.nutrihealthplan.dietapp.model.enums.UnitType;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class ProductCreateRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Kcal per 100g cannot be null")
    private BigDecimal kcalPer100g;
    @NotNull(message = "Fat per 100g cannot be null")
    private BigDecimal fatPer100g;
    private List<UnitInfo> units;
    private Scope scope;


}
