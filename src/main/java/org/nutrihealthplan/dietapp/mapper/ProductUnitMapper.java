package org.nutrihealthplan.dietapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.nutrihealthplan.dietapp.dto.UnitInfo;
import org.nutrihealthplan.dietapp.model.ProductUnitEntity;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ProductUnitMapper {

    @Mapping(source ="unitType", target = "type")
    @Mapping(source = "gramsPerUnit", target = "grams")
    UnitInfo toDto(ProductUnitEntity entity);
    List<UnitInfo> toDto(List<ProductUnitEntity> entities);
}
