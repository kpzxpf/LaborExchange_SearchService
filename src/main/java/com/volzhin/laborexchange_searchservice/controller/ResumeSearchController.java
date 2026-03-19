package com.volzhin.laborexchange_searchservice.controller;

import com.volzhin.laborexchange_searchservice.dto.PageResponse;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchRequest;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchResponse;
import com.volzhin.laborexchange_searchservice.service.search.SearchResumeService;
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

@Tag(name = "Resume Search")
@RestController
@RequestMapping("/api/search/resumes")
@RequiredArgsConstructor
public class ResumeSearchController {

    private final SearchResumeService searchResumeService;

    @Operation(
            summary = "Search resumes",
            description = """
                    Full-text + filter search over the Elasticsearch `resumes` index.

                    **Filtering logic:**
                    - `query` — multi-match over `title` (^3), `summary` (^1), `institutions` (^2) with AUTO fuzziness
                    - `skills` — each skill must be present (AND); case-insensitive keyword match
                    - `experienceYearsMin` / `experienceYearsMax` — inclusive range filter

                    Results are sorted by relevance score descending.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results page"),
            @ApiResponse(responseCode = "400", description = "Validation error in query parameters"),
            @ApiResponse(responseCode = "503", description = "Elasticsearch unavailable")
    })
    @GetMapping
    public ResponseEntity<PageResponse<ResumeSearchResponse>> search(
            @Valid @ModelAttribute ResumeSearchRequest request) {
        return ResponseEntity.ok(searchResumeService.search(request));
    }
}
