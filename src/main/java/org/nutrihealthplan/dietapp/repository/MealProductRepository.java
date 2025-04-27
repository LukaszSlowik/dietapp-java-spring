package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.model.MealProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface MealProductRepository extends JpaRepository<MealProductEntity,Long> {
    @Query("SELECT COALESCE(MAX(m.mealNumber),0) FROM MealProductEntity m WHERE m.owner.id = :ownerId AND m.mealDate = :mealDate")
    int findMaxMealNumberByOwnerAndDate(
            @Param("ownerId") Long ownerId,
                    @Param("mealDate") LocalDate mealDate);
}
