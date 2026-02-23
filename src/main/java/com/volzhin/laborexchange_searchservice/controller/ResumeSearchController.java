package com.volzhin.laborexchange_searchservice.controller;

import com.volzhin.laborexchange_searchservice.dto.PageResponse;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchRequest;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchResponse;
import com.volzhin.laborexchange_searchservice.service.search.SearchResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search/resumes")
@RequiredArgsConstructor
public class ResumeSearchController {

    private final SearchResumeService searchResumeService;

    @GetMapping
    public ResponseEntity<PageResponse<ResumeSearchResponse>> search(
            @Valid @ModelAttribute ResumeSearchRequest request) {
        return ResponseEntity.ok(searchResumeService.search(request));
    }
}