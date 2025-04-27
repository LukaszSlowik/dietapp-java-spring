package org.nutrihealthplan.dietapp.utils;

import lombok.AllArgsConstructor;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.ProductUnitEntity;
import org.nutrihealthplan.dietapp.model.enums.UnitType;
import org.nutrihealthplan.dietapp.repository.ProductUnitRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
@AllArgsConstructor
public class Conversions {
    private final ProductUnitRepository productUnitRepository;

    public BigDecimal convertToGrams(UnitType unitType, BigDecimal amount, ProductEntity productEntity) {
        if (unitType == null) {
            return amount;
        }

        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (productEntity == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        ProductUnitEntity productUnitEntity = productUnitRepository.findByProductAndUnitType(productEntity, unitType);

        if (productUnitEntity == null) {
            throw new IllegalArgumentException("No conversion found for unit: " + unitType + " for product: " + productEntity.getName());
        }

        return amount.multiply(productUnitEntity.getGramsPerUnit());
    }
}
