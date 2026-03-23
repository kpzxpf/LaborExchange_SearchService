package com.volzhin.laborexchange_searchservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Schema(description = "Resume search result item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeSearchResponse {

    @Schema(description = "Elasticsearch document ID (equals resume ID as string)", example = "9")
    private String id;

    @Schema(description = "Resume title", example = "Java Developer CV")
    private String title;

    @Schema(description = "Professional summary", example = "5 years of backend development...")
    private String summary;

    @Schema(description = "Total years of work experience", example = "5")
    private Integer experienceYears;

    @Schema(description = "Candidate's skills", example = "[\"Java\", \"Spring Boot\", \"Docker\"]")
    private Set<String> skills;

    @Schema(description = "Candidate's location", example = "Moscow")
    private String location;

    @Schema(description = "Educational institutions attended", example = "[\"Moscow State University\"]")
    private Set<String> institutions;

    @Schema(description = "Expected monthly salary in rubles", example = "180000")
    private Integer expectedSalary;
}
