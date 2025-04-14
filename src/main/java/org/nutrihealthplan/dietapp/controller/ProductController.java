package org.nutrihealthplan.dietapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.nutrihealthplan.dietapp.dto.ProductBasicInfoResponse;
import org.nutrihealthplan.dietapp.dto.ProductCreateResponse;
import org.nutrihealthplan.dietapp.dto.ProductCreateRequest;
import org.nutrihealthplan.dietapp.dto.ProductsGetResponse;
import org.nutrihealthplan.dietapp.dto.filter.ProductFilter;
import org.nutrihealthplan.dietapp.model.ResponseApi;
import org.nutrihealthplan.dietapp.model.ResponseApiFactory;
import org.nutrihealthplan.dietapp.repository.ProductRepository;
import org.nutrihealthplan.dietapp.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;




    @PostMapping("")
    public ResponseEntity<ResponseApi<List<ProductCreateResponse>>> createProduct(
            @Valid @RequestBody List<ProductCreateRequest> productCreateRequests,
            HttpServletRequest request
            )
    {
        String path = request.getRequestURI();
        List<ProductCreateResponse> response = productService.createProduct(productCreateRequests);
        return  ResponseEntity.ok(ResponseApiFactory.success(response,path));
    }
    @GetMapping("")
    public ResponseEntity<ResponseApi<List<ProductBasicInfoResponse>>> getProducts(
            @ModelAttribute ProductFilter filter,
            Pageable pageable,
            HttpServletRequest request
    )
    {
        String path = request.getRequestURI();
        Page<ProductBasicInfoResponse> products = productService.getProducts(filter, pageable, path);
        return ResponseEntity.ok(ResponseApiFactory.paginatedSuccess(products, path));
    }
}
