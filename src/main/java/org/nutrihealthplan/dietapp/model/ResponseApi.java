package org.nutrihealthplan.dietapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.Instant;
@Getter
@AllArgsConstructor
public class ResponseApi<T> {
    private String status;
    private String code;
    private String message;
    private T data;
    private PaginationMetadata pagination;
    private Instant timestamp;
    private String path;
    @Getter
    @AllArgsConstructor
    public static class PaginationMetadata {
        private int currentPage;
        private int pageSize;
        private long totalItems;
        private int totalPages;
        private boolean firstPage;
        private boolean lastPage;
        private boolean hasNext;
        private boolean hasPrevious;
    }
}