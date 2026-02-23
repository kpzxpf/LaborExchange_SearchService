package com.volzhin.laborexchange_searchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VacancySearchResponse {
    private String id;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private Double salary;
    private Set<String> skills;
    private LocalDateTime createdAt;
}