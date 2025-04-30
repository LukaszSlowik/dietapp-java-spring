package org.nutrihealthplan.dietapp.service;

import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.dto.PublicProductResponse;
import org.nutrihealthplan.dietapp.dto.filter.PublicProductFilter;
import org.nutrihealthplan.dietapp.mapper.ProductMapper;
import org.nutrihealthplan.dietapp.model.ProductEntity;
import org.nutrihealthplan.dietapp.repository.ProductRepository;
import org.nutrihealthplan.dietapp.repository.ProductSpecifications;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class PublicProductCacheServiceImpl implements PublicProductCacheService {
    public final ProductRepository productRepository;
    public final ProductMapper productMapper;
    @Cacheable(
            value = "globalProductListCache",
            key = "T(org.nutrihealthplan.dietapp.utils.CacheKeyUtils).buildKey(#productFilter, #pageable)"
    )
    @Override

    public List<PublicProductResponse> getCachedPublicProducts(PublicProductFilter productFilter, Pageable pageable) {

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
        return content;

    }



}
