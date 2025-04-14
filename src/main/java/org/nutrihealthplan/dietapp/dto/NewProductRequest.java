package org.nutrihealthplan.dietapp.dto;

import java.math.BigDecimal;

public record NewProductRequest(
        String name,
        BigDecimal weight
) {
}
