package com.volzhin.laborexchange_searchservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ResumeSearchResponse {
    private String id;
    private String title;
    private String summary;
    private Integer experienceYears;
    private Set<String> skills;
    private Set<String> institutions;
}