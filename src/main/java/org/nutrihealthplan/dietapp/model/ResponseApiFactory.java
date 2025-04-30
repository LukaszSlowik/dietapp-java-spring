package org.nutrihealthplan.dietapp.model;

import org.nutrihealthplan.dietapp.dto.ProductBasicInfoResponse;
import org.nutrihealthplan.dietapp.dto.ProductsGetResponse;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.List;

public class ResponseApiFactory {
    public static <T> ResponseApi<T> success(T data, String path) {
        return new ResponseApi<>("success", "200_OK", "Operation successful", data, null, Instant.now(), path);
    }

    public static ResponseApi<ProductsGetResponse> paginatedSuccessProducts(Page<ProductBasicInfoResponse> page, String path) {
        ProductsGetResponse productsGetResponse = ProductsGetResponse.builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();

        return new ResponseApi<>(
                "success",
                "200_OK",
                "Operation successful",
                productsGetResponse,
                null,
                Instant.now(),
                path
        );
    }
    public static <T> ResponseApi<List<T>> paginatedSuccess(Page<T> page, String path) {

        ResponseApi.PaginationMetadata paginationMetadata = null;

        if (page != null) {
            paginationMetadata = new ResponseApi.PaginationMetadata(
                    page.getNumber(),                    // currentPage
                    page.getSize(),                      // pageSize
                    page.getTotalElements(),             // totalItems
                    page.getTotalPages(),                // totalPages
                    page.isFirst(),                      // firstPage
                    page.isLast(),                       // lastPage
                    page.hasNext(),                      // hasNext
                    page.hasPrevious()                   // hasPrevious
            );
        }


        assert page != null;
        return new ResponseApi<>(
                "success",
                "200_OK",
                "Operation successful",
                page.getContent(),
                paginationMetadata,
                Instant.now(),
                path
        );
    }

    public static <T> ResponseApi<T> error(String code, String message, String path) {
        return new ResponseApi<>("error", code, message, null, null, Instant.now(), path);
    }
}
