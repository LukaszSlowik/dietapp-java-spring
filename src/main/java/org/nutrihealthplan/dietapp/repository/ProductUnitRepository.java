package org.nutrihealthplan.dietapp.repository;

import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.model.ProductUnitEntity;
import org.nutrihealthplan.dietapp.model.enums.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductUnitRepository extends JpaRepository<ProductUnitEntity,Long> {
    ProductUnitEntity findByProductAndUnitType(ProductEntity product, UnitType unitType);
}
