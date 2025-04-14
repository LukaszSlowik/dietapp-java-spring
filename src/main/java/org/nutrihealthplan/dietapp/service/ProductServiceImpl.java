package org.nutrihealthplan.dietapp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrihealthplan.dietapp.dto.*;
import org.nutrihealthplan.dietapp.dto.filter.ProductFilter;
import org.nutrihealthplan.dietapp.exceptions.ProductNotCreatedException;
import org.nutrihealthplan.dietapp.mapper.ProductMapper;
import org.nutrihealthplan.dietapp.model.*;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.nutrihealthplan.dietapp.model.enums.UnitType;
import org.nutrihealthplan.dietapp.repository.ProductRepository;
import org.nutrihealthplan.dietapp.repository.ProductSpecifications;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final AuditorAware<UserEntity> auditorAware;

    public List<ProductCreateResponse> createProduct(
            List<ProductCreateRequest> requests) {

        List<ProductCreateResponse> responses = new ArrayList<>();


        for(ProductCreateRequest request : requests) {
        try {

            Scope scope = request.getScope() != null ? request.getScope() : Scope.PRIVATE;
            UserEntity owner = auditorAware.getCurrentAuditor()
                    .orElseThrow(()->
                            new IllegalStateException("No authenticated user found"));

            ProductEntity productToCreate = ProductEntity.builder()
                    .name(request.getName())
                    .kcalPer100g(request.getKcalPer100g())
                    .fatPer100g(request.getFatPer100g())
                    .scope(scope)
                    .owner(owner)
                    .build();

            if(request.getUnits() !=null){
                List<ProductUnitEntity> productUnitEntities = new ArrayList<>();
                for (UnitInfo unitRequest : request.getUnits()) {
                    ProductUnitEntity  unitEntity = ProductUnitEntity.builder()
                            .unitType(UnitType.valueOf(unitRequest.getType()))
                            .gramsPerUnit(unitRequest.getGrams())
                            .product(productToCreate)
                            .build();
                    productUnitEntities.add(unitEntity);
                }
                productToCreate.setProductUnitEntities(productUnitEntities);
            }


            ProductEntity createdProduct = productRepository.save(productToCreate);
            responses.add(productMapper.toCreatedProductResponse(createdProduct));

        } catch (DataIntegrityViolationException ex) {
            log.error("Error creating product due to data integrity violation: {}", ex.getMessage(), ex);
            throw new ProductNotCreatedException("Failed to create product due to data integrity issues.");
        } catch (Exception ex) {
            log.error("Unexpected error occurred while creating product: {}", ex.getMessage(), ex);
            throw new ProductNotCreatedException("Unexpected error occurred during product creation.");
        }
    }
        return responses;
}

    @Override
        public Page<ProductBasicInfoResponse> getProducts(ProductFilter filter, Pageable pageable, String path) {
        try {
            Specification<ProductEntity> spec = ProductSpecifications.buildFromFilter(filter);


            Page<ProductEntity> productPage = productRepository.findAll(spec, pageable);
            List<ProductBasicInfoResponse> content = productPage
                    .getContent()
                    .stream()
                    .map(productMapper::toBasicInfo)
                    .toList();


            return new PageImpl<>(content, pageable, productPage.getTotalElements());

        } catch (DataIntegrityViolationException ex) {
            log.error("Error fetching products due to data integrity violation: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to fetch products due to data integrity issues.");
        } catch (Exception ex) {
            log.error("Unexpected error occurred while fetching products: {}", ex.getMessage(), ex);
            throw new RuntimeException("Unexpected error occurred during product retrieval.");
        }
    }
}
