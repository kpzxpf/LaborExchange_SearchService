package com.volzhin.laborexchange_searchservice.controller;

import com.volzhin.laborexchange_searchservice.dto.SuggestResponse;
import com.volzhin.laborexchange_searchservice.service.SuggestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autocomplete Suggestions",
        description = "Prefix-based autocomplete for vacancy titles, company names, locations, and skills. " +
                "Used for real-time search input suggestions in the frontend.")
@RestController
@RequestMapping("/api/search/suggest")
@RequiredArgsConstructor
public class SuggestController {

    private final SuggestService suggestService;

    @Operation(
            summary = "Get autocomplete suggestions",
            description = "Returns up to 10 deduplicated suggestions for the given prefix query. " +
                    "Minimum 2 characters required. " +
                    "Types: `vacancy` (job titles), `company` (company names), `location` (cities/regions), `skill` (skill names)."
    )
    @ApiResponse(responseCode = "200", description = "Suggestions list",
            content = @Content(schema = @Schema(implementation = SuggestResponse.class)))
    @GetMapping
    public SuggestResponse suggest(
            @Parameter(description = "Prefix query (minimum 2 characters)", example = "Java", required = true)
            @RequestParam String q,
            @Parameter(description = "Suggestion type", example = "vacancy",
                    schema = @Schema(allowableValues = {"vacancy", "company", "location", "skill"}))
            @RequestParam(defaultValue = "vacancy") String type) {
        return suggestService.suggest(q, type);
    }
}
