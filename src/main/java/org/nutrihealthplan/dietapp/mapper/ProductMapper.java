package org.nutrihealthplan.dietapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.nutrihealthplan.dietapp.dto.ProductBasicInfoResponse;
import org.nutrihealthplan.dietapp.dto.ProductCreateResponse;
import org.nutrihealthplan.dietapp.model.ProductEntity;

@Mapper(componentModel = "spring", uses = {ProductUnitMapper.class, OwnerInfoMapper.class})
public interface ProductMapper {
    //ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "scope", target = "scope")
    ProductCreateResponse toCreatedProductResponse(ProductEntity productEntity);
    @Mapping(source = "productUnitEntities", target = "units")
    //@Mapping(target = "owner", ignore = true)
    ProductBasicInfoResponse toBasicInfo(ProductEntity product);
}
