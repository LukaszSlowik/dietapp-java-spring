package org.nutrihealthplan.dietapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.nutrihealthplan.dietapp.dto.PublicProductResponse;
import org.nutrihealthplan.dietapp.dto.filter.PublicProductFilter;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.ResponseApiFactory;
import org.nutrihealthplan.dietapp.service.PublicProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/products")
public class PublicProductController {
    private final PublicProductService publicProductService;


    @GetMapping

    public ResponseEntity<ResponseApi<List<PublicProductResponse>>> getPublicProducts(@ModelAttribute PublicProductFilter filter,
                                                                                      Pageable pageable,
                                                                                      HttpServletRequest request) {
        String path = request.getServletPath();
        Page<PublicProductResponse> publicProducts = publicProductService.getPublicProducts(filter, pageable, path);
        return ResponseEntity.ok(ResponseApiFactory.paginatedSuccess(publicProducts, path));

    }
}
