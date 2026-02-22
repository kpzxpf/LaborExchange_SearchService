package com.volzhin.laborexchange_searchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyIndexEvent {
    private Long id;
    private String title;
    private String description;
    private String companyName;
    private String location;
    private Set<String> skills;
}
