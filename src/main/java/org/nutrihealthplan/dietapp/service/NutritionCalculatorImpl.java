package org.nutrihealthplan.dietapp.service;

import org.nutrihealthplan.dietapp.dto.meal.NutritionInfo;
import org.nutrihealthplan.dietapp.model.MealProductEntity;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class NutritionCalculatorImpl implements NutritionCalculator{

    @Override
    public NutritionInfo calculateTotalNutrition(List<MealProductEntity> entities) {
        BigDecimal kcal = BigDecimal.ZERO;
        BigDecimal fat = BigDecimal.ZERO;
        BigDecimal protein = BigDecimal.ZERO;
        BigDecimal carbs = BigDecimal.ZERO;

        for (MealProductEntity e : entities) {
            ProductEntity p = e.getProduct();
            BigDecimal factor = e.getGrams().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
            kcal = kcal.add(p.getKcalPer100g().multiply(factor));
            fat = fat.add(p.getFatPer100g().multiply(factor));
            protein = protein.add(p.getProteinPer100g().multiply(factor));
            carbs = carbs.add(p.getCarbsPer100g().multiply(factor));
        }

        return new NutritionInfo(kcal, fat, protein, carbs);
    }

    @Override
    public NutritionInfo calculateNutrition(MealProductEntity entity) {
        ProductEntity p = entity.getProduct();
        BigDecimal factor = entity.getGrams().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        return new NutritionInfo(
                p.getKcalPer100g().multiply(factor),
                p.getFatPer100g().multiply(factor),
                p.getProteinPer100g().multiply(factor),
                p.getCarbsPer100g().multiply(factor)
        );
    }
}
