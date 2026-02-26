package com.volzhin.laborexchange_searchservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancySearchRequest {

    private String query;

    private Set<String> skills;

    private String location;

    @Positive
    private Double salaryMin;

    @Positive
    private Double salaryMax;

    @Min(0)
    private int page = 0;

    @Min(1)
    @Max(100)
    private int size = 10;
}
