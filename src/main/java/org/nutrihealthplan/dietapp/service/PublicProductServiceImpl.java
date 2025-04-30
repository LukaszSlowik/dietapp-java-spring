package org.nutrihealthplan.dietapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nutrihealthplan.dietapp.dto.PublicProductResponse;
import org.nutrihealthplan.dietapp.dto.filter.PublicProductFilter;
import org.nutrihealthplan.dietapp.mapper.ProductMapper;
import org.nutrihealthplan.dietapp.repository.ProductRepository;
import org.nutrihealthplan.dietapp.repository.ProductSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PublicProductServiceImpl implements PublicProductService{
    public final ProductRepository productRepository;
    public final ProductMapper productMapper;
    public final PublicProductCacheService publicProductCacheService;


    @Override
    public Page<PublicProductResponse> getPublicProducts(PublicProductFilter productFilter,
                                                         Pageable pageable,
                                                         String path) {

        long total = productRepository.count(ProductSpecifications.buildFromPublicFilter(productFilter));
        List<PublicProductResponse> content = publicProductCacheService.getCachedPublicProducts(productFilter,pageable);
        return new PageImpl<>(content, pageable, total);

    }

   }
