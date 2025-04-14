package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.model.MealProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealProductRepository extends JpaRepository<MealProductEntity,Long> {
}
