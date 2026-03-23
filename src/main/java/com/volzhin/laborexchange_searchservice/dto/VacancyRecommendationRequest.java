package com.volzhin.laborexchange_searchservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyRecommendationRequest {

    @NotEmpty(message = "skills must not be empty")
    private Set<String> skills;

    private String location;

    @Positive(message = "salaryMin must be positive")
    private Double salaryMin;

    @Min(0)
    private int page = 0;

    @Min(1) @Max(100)
    private int size = 10;
}
