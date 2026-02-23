package com.volzhin.laborexchange_searchservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Set;

@Data
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
