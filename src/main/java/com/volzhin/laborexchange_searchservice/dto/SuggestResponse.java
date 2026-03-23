package com.volzhin.laborexchange_searchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Autocomplete suggestion response")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestResponse {

    @Schema(description = "The search query that was used", example = "java")
    private String query;

    @Schema(description = "Type of suggestions", example = "vacancy",
            allowableValues = {"vacancy", "company", "location", "skill"})
    private String type;

    @Schema(description = "List of suggestion strings (deduplicated, max 10)")
    private List<String> suggestions;
}
