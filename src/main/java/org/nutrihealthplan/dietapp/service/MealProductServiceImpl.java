package org.nutrihealthplan.dietapp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.nutrihealthplan.dietapp.dto.meal.*;
import org.nutrihealthplan.dietapp.mapper.MealProductMapper;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.User;
import org.nutrihealthplan.dietapp.repository.MealProductRepository;
import org.nutrihealthplan.dietapp.model.MealProductEntity;
import org.nutrihealthplan.dietapp.repository.ProductRepository;
import org.nutrihealthplan.dietapp.repository.ProductUnitRepository;
import org.nutrihealthplan.dietapp.repository.UserRepository;
import org.nutrihealthplan.dietapp.utils.Conversions;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MealProductServiceImpl implements MealProductService {
    private final MealProductRepository mealProductRepository;
    private final CurrentUserService currentUserService;
    private final ProductRepository productRepository;
    private final ProductUnitRepository productUnitRepository;
    private final UserRepository userRepository;
    private final MealProductMapper mealProductMapper;
    private final Conversions conversions;
    private final NutritionCalculator nutritionCalculator;

@Override
    public MealProductCreateResponse createMeal(MealCreateRequest request) {
        if (request == null ) {
            throw new IllegalArgumentException("Request cannot be empty.");
        }

    LocalDate mealDate = Optional.ofNullable(request.getMealDate()).orElse(LocalDate.now());
        User owner = resolveOwner(request.getOwnerId());
    UUID mealId = UUID.randomUUID();

        int nextMealNumber = mealProductRepository
                .findMaxMealNumberByOwnerAndDate(owner.getId(), mealDate) + 1;


        List<MealProductEntity> products = new ArrayList<>();

        for (MealProductCreateRequest req : request.getProducts()) {
            ProductEntity product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + req.getProductId()));

            MealProductEntity entity = MealProductEntity.builder()
                    .owner(owner)
                    .mealDate(mealDate)
                    .mealNumber(nextMealNumber)
                    .mealId(mealId)
                    .product(product)
                    .unitType(req.getUnitType())
                    .amount(req.getAmount())
                    .grams(conversions.convertToGrams(req.getUnitType(),req.getAmount(),product))
                    .build();
            products.add(entity);
        }
        mealProductRepository.saveAll(products);
    List<MealProductsCreated> mealProductsCreated = products
            .stream()
            .map(entity-> MealProductsCreated.builder()
                            .productId(entity.getProduct().getId())
                            .productName(entity.getProduct().getName())
                            .unitType(entity.getUnitType())
                            .amount(entity.getAmount())
                            .grams(entity.getGrams())
                            .nutrition(nutritionCalculator.calculateNutrition(entity))
                            .build()
                    ).toList();


    return MealProductCreateResponse.builder()
                .ownerId(owner.getId())
                .mealDate(mealDate)
                .products(mealProductsCreated)
            .nutrition(nutritionCalculator.calculateTotalNutrition(products))
                .build();
    }

    @Override
    public User resolveOwner(Long ownerId) {
        if(ownerId != null)
        {

            //TODO: Add check if canCreateMealsForUser
            return userRepository.findById(ownerId).orElseThrow(
                    () -> new EntityNotFoundException("User with id " + ownerId + " not found")
            );
        }
        return currentUserService.getCurrentUser();
    }

}
