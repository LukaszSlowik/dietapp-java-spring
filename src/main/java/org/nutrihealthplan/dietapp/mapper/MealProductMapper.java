package org.nutrihealthplan.dietapp.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.nutrihealthplan.dietapp.dto.meal.MealProductCreateResponse;
import org.nutrihealthplan.dietapp.dto.meal.MealProductsCreated;
import org.nutrihealthplan.dietapp.dto.meal.NutritionInfo;
import org.nutrihealthplan.dietapp.model.MealProductEntity;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.service.NutritionCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MealProductMapper {
//    @Mapping(target = "products", source = "products")
//    @Mapping(target = "mealDate", source = "mealDate")
//    @Mapping(target = "mealId", source = "mealId")
//    @Mapping(target = "nutrition", expression = "java(nutritionCalculator.calculateTotalNutrition(source.getProducts()))")
//    @Mapping(target = "message", constant = "Meal created successfully")
//    MealProductCreateResponse toCreateResponse(
//            MealMappingSource source,
//            @Context NutritionCalculator nutritionCalculator); //with List<MealProductsCreated> products;

//    @Mapping(target = "productId", source = "product.id")
//    @Mapping(target = "name", source = "product.name")
//    @Mapping(target = "unitType", source = "unitType")
//    @Mapping(target = "amount", source = "amount")
//    @Mapping(target = "grams", source = "grams")
//    @Mapping(target = "nutrition", expression = "java(nutritionCalculator.calculateNutrition(entity))")
//    MealProductsCreated toMealProductsCreated(MealProductEntity entity,
//                                              @Context NutritionCalculator nutritionCalculator);
//
//
//
//    List<MealProductsCreated> toProductsCreatedList(List<MealProductEntity> entities);



}
