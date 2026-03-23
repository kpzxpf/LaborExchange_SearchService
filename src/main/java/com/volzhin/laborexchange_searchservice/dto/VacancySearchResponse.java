package com.volzhin.laborexchange_searchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "Vacancy search result item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancySearchResponse {

    @Schema(description = "Elasticsearch document ID (equals vacancy ID as string)", example = "7")
    private String id;

    @Schema(description = "Job title", example = "Senior Java Developer")
    private String title;

    @Schema(description = "Job description snippet", example = "We are looking for...")
    private String description;

    @Schema(description = "Company name", example = "Acme Corp")
    private String companyName;

    @Schema(description = "Office location", example = "Moscow")
    private String location;

    @Schema(description = "Monthly salary", example = "250000.0")
    private Double salary;

    @Schema(description = "Required skills", example = "[\"Java\", \"Spring Boot\", \"PostgreSQL\"]")
    private Set<String> skills;

    @Schema(description = "Employment type", example = "FULL_TIME")
    private String employmentType;

    @Schema(description = "Work format", example = "REMOTE")
    private String workFormat;

    @Schema(description = "Vacancy creation timestamp", example = "2026-03-20T12:00:00")
    private LocalDateTime createdAt;
}
