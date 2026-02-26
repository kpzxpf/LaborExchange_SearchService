package com.volzhin.laborexchange_searchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeSearchResponse {
    private String id;
    private String title;
    private String summary;
    private Integer experienceYears;
    private Set<String> skills;
    private Set<String> institutions;
}