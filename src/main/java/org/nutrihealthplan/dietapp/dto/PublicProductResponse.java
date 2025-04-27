package org.nutrihealthplan.dietapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicProductResponse {
    private Long id;
    private String name;
    private BigDecimal kcalPer100g;
    private BigDecimal fatPer100g;
    private BigDecimal proteinPer100g;
    private BigDecimal carbsPer100g;
    private List<UnitInfo> units;
}
