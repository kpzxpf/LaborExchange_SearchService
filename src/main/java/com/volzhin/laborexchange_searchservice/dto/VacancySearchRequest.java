package com.volzhin.laborexchange_searchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(description = "Vacancy search parameters (all fields optional)")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancySearchRequest {

    @Schema(description = "Free-text search query (fuzzy, multi-field)", example = "Java developer")
    private String query;

    @Schema(description = "Required skills — all must match (case-insensitive AND logic)", example = "[\"Java\", \"Spring Boot\"]")
    private Set<String> skills;

    @Schema(description = "City / location — exact keyword match", example = "Moscow")
    private String location;

    @Schema(description = "Minimum salary (inclusive)", example = "100000")
    @Positive
    private Double salaryMin;

    @Schema(description = "Maximum salary (inclusive)", example = "300000")
    @Positive
    private Double salaryMax;

    @Schema(description = "Page number (0-based)", example = "0", defaultValue = "0")
    @Min(0)
    private int page = 0;

    @Schema(description = "Page size (1–100)", example = "10", defaultValue = "10")
    @Min(1)
    @Max(100)
    private int size = 10;
}
