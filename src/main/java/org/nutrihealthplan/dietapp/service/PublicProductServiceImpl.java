package org.nutrihealthplan.dietapp.service;

import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.dto.PublicProductResponse;
import org.nutrihealthplan.dietapp.dto.UnitInfo;
import org.nutrihealthplan.dietapp.dto.filter.PublicProductFilter;
import org.nutrihealthplan.dietapp.mapper.ProductMapper;
import org.nutrihealthplan.dietapp.mapper.ProductUnitMapper;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.repository.ProductRepository;
import org.nutrihealthplan.dietapp.repository.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicProductServiceImpl implements PublicProductService{
    public final ProductRepository productRepository;
    //public final ProductUnitMapper productUnitMapper;
    public final ProductMapper productMapper;
    @Override
    public Page<PublicProductResponse> getPublicProducts(PublicProductFilter productFilter,
                                                         Pageable pageable,
                                                         String path) {
        Specification<ProductEntity> productEntitySpecification = ProductSpecifications.buildFromPublicFilter(productFilter);
        Page<ProductEntity> productPage = productRepository.findAll(productEntitySpecification, pageable);
        List<PublicProductResponse> content = productPage
                .getContent()
                .stream()
                .map(
                        productMapper::toPublicProductResponse)
                //if without mapper :
//                        productEntity -> PublicProductResponse.builder()
//                        .id(productEntity.getId())
//                        .name(productEntity.getName())
//                        .kcalPer100g(productEntity.getKcalPer100g())
//                        .fatPer100g(productEntity.getFatPer100g())
//                        .carbsPer100g(productEntity.getCarbsPer100g())
//                        .proteinPer100g(productEntity.getProteinPer100g())
////                        .units(productEntity.getProductUnitEntities()
////                                .stream()
////                                .map(unit-> UnitInfo
////                                        .builder()
////                                        .type(unit.getUnitType())
////                                        .grams(unit.getGramsPerUnit())
////                                        .build())
////                                .toList())
//                                .units(productUnitMapper.toDto(productEntity.getProductUnitEntities()))
//                        .build()
//                )
                .toList();

        return new PageImpl<>(content, pageable, productPage.getTotalElements());

    }
}
