package com.volzhin.laborexchange_searchservice.controller;

import com.volzhin.laborexchange_searchservice.dto.PageResponse;
import com.volzhin.laborexchange_searchservice.dto.VacancyRecommendationRequest;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchRequest;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchResponse;
import com.volzhin.laborexchange_searchservice.service.search.SearchVacancyService;
import com.volzhin.laborexchange_searchservice.service.search.VacancyRecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Vacancy Search")
@RestController
@RequestMapping("/api/search/vacancies")
@RequiredArgsConstructor
public class VacancySearchController {

    private final SearchVacancyService searchVacancyService;
    private final VacancyRecommendationService recommendationService;

    @Operation(
            summary = "Search vacancies",
            description = """
                    Full-text + filter search over the Elasticsearch `vacancies` index.

                    **Filtering logic:**
                    - `query` — multi-match over `title` (^3), `description` (^1), `companyName` (^2) with AUTO fuzziness
                    - `skills` — each skill must be present (AND); case-insensitive keyword match
                    - `location` — exact keyword match
                    - `salaryMin` / `salaryMax` — inclusive range filter

                    Results are sorted by relevance score descending.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results page"),
            @ApiResponse(responseCode = "400", description = "Validation error in query parameters"),
            @ApiResponse(responseCode = "503", description = "Elasticsearch unavailable")
    })
    @GetMapping
    public ResponseEntity<PageResponse<VacancySearchResponse>> search(
            @Valid @ModelAttribute VacancySearchRequest request) {
        return ResponseEntity.ok(searchVacancyService.search(request));
    }

    @Operation(
            summary = "Get vacancy recommendations",
            description = """
                    Returns vacancies ranked by skill overlap with the user's profile.
                    Each matching skill adds to the relevance score — vacancies matching more skills rank higher.
                    Requires at least one matching skill (`minimumShouldMatch=1`).

                    **Parameters:**
                    - `skills` — list of skill names from the user's resume (required)
                    - `location` — optional location filter (exact match)
                    - `salaryMin` — optional minimum salary filter
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recommended vacancies ranked by skill match"),
            @ApiResponse(responseCode = "400", description = "Validation error — skills must not be empty"),
            @ApiResponse(responseCode = "503", description = "Elasticsearch unavailable")
    })
    @GetMapping("/recommendations")
    public ResponseEntity<PageResponse<VacancySearchResponse>> recommend(
            @Valid @ModelAttribute VacancyRecommendationRequest request) {
        return ResponseEntity.ok(recommendationService.recommend(request));
    }
}
