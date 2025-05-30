package org.nutrihealthplan.dietapp.service;

import org.nutrihealthplan.dietapp.dto.ProductBasicInfoResponse;
import org.nutrihealthplan.dietapp.dto.PublicProductResponse;
import org.nutrihealthplan.dietapp.dto.filter.ProductFilter;
import org.nutrihealthplan.dietapp.dto.filter.PublicProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublicProductService {
    Page<PublicProductResponse> getPublicProducts(PublicProductFilter productFilter, Pageable pageable, String path);
    //List<PublicProductResponse> getCachedPublicProducts(PublicProductFilter productFilter, Pageable pageable);
}
