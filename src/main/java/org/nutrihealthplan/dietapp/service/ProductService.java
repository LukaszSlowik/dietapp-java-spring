package org.nutrihealthplan.dietapp.service;

import org.nutrihealthplan.dietapp.dto.ProductBasicInfoResponse;
import org.nutrihealthplan.dietapp.dto.ProductCreateRequest;
import org.nutrihealthplan.dietapp.dto.ProductCreateResponse;
import org.nutrihealthplan.dietapp.dto.ProductsGetResponse;
import org.nutrihealthplan.dietapp.dto.filter.ProductFilter;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.enums.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<ProductCreateResponse> createProduct(List<ProductCreateRequest> requests);
    Page<ProductBasicInfoResponse> getProducts(ProductFilter productFilter, Pageable pageable, String path);
   // Page<ProductBasicInfoResponse> getFilteredProducts(String name, Scope scope, Long userId, Pageable pageable);
    }


