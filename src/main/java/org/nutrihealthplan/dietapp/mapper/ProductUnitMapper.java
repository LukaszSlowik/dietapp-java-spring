package org.nutrihealthplan.dietapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nutrihealthplan.dietapp.dto.UnitInfo;
import org.nutrihealthplan.dietapp.model.ProductUnitEntity;

@Mapper(componentModel = "spring")
public interface ProductUnitMapper {

    @Mapping(source ="gramsPerUnit", target = "grams")
    @Mapping(source = "unitType", target = "type")
    UnitInfo toDto(ProductUnitEntity entity);
}
