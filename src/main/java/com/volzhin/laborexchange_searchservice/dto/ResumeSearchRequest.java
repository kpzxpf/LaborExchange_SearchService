package com.volzhin.laborexchange_searchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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

    @Schema(description = "Required skills — at least one must match, more matches rank higher (case-insensitive OR logic)", example = "[\"Java\", \"Docker\"]")
    private Set<String> skills;

    @Schema(description = "City / location — exact keyword match", example = "Moscow")
    private String location;

    @Schema(description = "Minimum years of experience (inclusive)", example = "2")
    @Min(0)
    private Integer experienceYearsMin;

    @Schema(description = "Maximum years of experience (inclusive)", example = "7")
    @Positive
    private Integer experienceYearsMax;

    @Schema(description = "Minimum expected salary (inclusive)", example = "100000")
    @PositiveOrZero
    private Integer expectedSalaryMin;

    @Schema(description = "Maximum expected salary (inclusive)", example = "250000")
    @Positive
    private Integer expectedSalaryMax;

    @Schema(description = "Sort field: RELEVANCE, EXPERIENCE, SALARY", example = "RELEVANCE", defaultValue = "RELEVANCE")
    private String sortBy = "RELEVANCE";

    @Schema(description = "Sort direction: ASC or DESC", example = "DESC", defaultValue = "DESC")
    private String sortOrder = "DESC";

    @Schema(description = "Page number (0-based)", example = "0", defaultValue = "0")
    @Min(0)
    private int page = 0;

    @Schema(description = "Page size (1–100)", example = "10", defaultValue = "10")
    @Min(1)
    @Max(100)
    private int size = 10;
}
