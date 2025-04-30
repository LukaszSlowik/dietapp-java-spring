package org.nutrihealthplan.dietapp.service;

import org.nutrihealthplan.dietapp.dto.PublicProductResponse;
import org.nutrihealthplan.dietapp.dto.filter.PublicProductFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PublicProductCacheService {
    List<PublicProductResponse> getCachedPublicProducts(PublicProductFilter productFilter, Pageable pageable);

}
