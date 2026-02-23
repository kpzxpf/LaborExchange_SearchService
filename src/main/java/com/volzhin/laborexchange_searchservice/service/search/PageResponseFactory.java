package com.volzhin.laborexchange_searchservice.service.search;

import com.volzhin.laborexchange_searchservice.dto.PageResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageResponseFactory {

    public <T> PageResponse<T> build(List<T> content, long totalHits, int page, int size) {
        int totalPages = (int) Math.ceil((double) totalHits / size);
        return PageResponse.<T>builder()
                .content(content)
                .totalElements(totalHits)
                .totalPages(totalPages)
                .currentPage(page)
                .pageSize(size)
                .hasNext(page < totalPages - 1)
                .hasPrevious(page > 0)
                .build();
    }
}