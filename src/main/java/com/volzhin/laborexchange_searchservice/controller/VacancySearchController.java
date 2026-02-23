package com.volzhin.laborexchange_searchservice.controller;

import com.volzhin.laborexchange_searchservice.dto.PageResponse;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchRequest;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchResponse;
import com.volzhin.laborexchange_searchservice.service.search.SearchVacancyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/vacancies")
@RequiredArgsConstructor
public class VacancySearchController {

    private final SearchVacancyService searchVacancyService;

    @GetMapping
    public ResponseEntity<PageResponse<VacancySearchResponse>> search(
            @Valid @ModelAttribute VacancySearchRequest request) {
        return ResponseEntity.ok(searchVacancyService.search(request));
    }
}