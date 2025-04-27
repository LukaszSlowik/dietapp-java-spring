package org.nutrihealthplan.dietapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.nutrihealthplan.dietapp.dto.ProductBasicInfoResponse;
import org.nutrihealthplan.dietapp.dto.ProductCreateResponse;
import org.nutrihealthplan.dietapp.dto.PublicProductResponse;
import org.nutrihealthplan.dietapp.model.ProductEntity;

@Mapper(
        componentModel = "spring",
        uses = {ProductUnitMapper.class, OwnerInfoMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR

)
public interface ProductMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "scope", target = "scope")
    ProductCreateResponse toCreatedProductResponse(ProductEntity productEntity);

    @Mapping(source = "productUnitEntities", target = "units")
    ProductBasicInfoResponse toBasicInfo(ProductEntity product);

    @Mapping(source = "productUnitEntities", target = "units")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "kcalPer100g", target = "kcalPer100g")
    @Mapping(source = "fatPer100g", target = "fatPer100g")
    @Mapping(source = "carbsPer100g", target = "carbsPer100g")
    @Mapping(source = "proteinPer100g", target = "proteinPer100g")
    PublicProductResponse toPublicProductResponse(ProductEntity product);
}
