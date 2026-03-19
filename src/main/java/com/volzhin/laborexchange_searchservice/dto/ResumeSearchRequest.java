package com.volzhin.laborexchange_searchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(description = "Resume search parameters (all fields optional)")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeSearchRequest {

    @Schema(description = "Free-text search query (fuzzy, multi-field)", example = "Java backend developer")
    private String query;

    @Schema(description = "Required skills — all must match (case-insensitive AND logic)", example = "[\"Java\", \"Docker\"]")
    private Set<String> skills;

    @Schema(description = "Minimum years of experience (inclusive)", example = "2")
    @Min(0)
    private Integer experienceYearsMin;

    @Schema(description = "Maximum years of experience (inclusive)", example = "7")
    @Positive
    private Integer experienceYearsMax;

    @Schema(description = "Page number (0-based)", example = "0", defaultValue = "0")
    @Min(0)
    private int page = 0;

    @Schema(description = "Page size (1–100)", example = "10", defaultValue = "10")
    @Min(1)
    @Max(100)
    private int size = 10;
}
