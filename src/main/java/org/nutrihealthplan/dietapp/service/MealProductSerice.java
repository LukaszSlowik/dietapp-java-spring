package org.nutrihealthplan.dietapp.service;

import lombok.AllArgsConstructor;
import org.nutrihealthplan.dietapp.repository.MealProductRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MealProductSerice {
    private final MealProductRepository mealProductRepository;


}
