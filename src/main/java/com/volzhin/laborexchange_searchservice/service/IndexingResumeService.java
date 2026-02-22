package com.volzhin.laborexchange_searchservice.service;

import com.volzhin.laborexchange_searchservice.dto.ResumeIndexEvent;
import com.volzhin.laborexchange_searchservice.entity.ResumeIndex;
import com.volzhin.laborexchange_searchservice.repository.ResumeElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexingResumeService {

    private final ResumeElasticRepository resumeRepository;

    public void indexVacancy(ResumeIndexEvent resumeIndexEvent) {
        log.info("Indexing vacancy: {}", resumeIndexEvent.getTitle());

        ResumeIndex indexDocument = ResumeIndex.builder()
                .id(resumeIndexEvent.getId().toString())
                .title(resumeIndexEvent.getTitle())
                .summary(resumeIndexEvent.getSummary())
                .experienceYears(resumeIndexEvent.getExperienceYears())
                .institutions(resumeIndexEvent.getInstitutions())
                .skills(resumeIndexEvent.getSkills())
                .build();

        resumeRepository.save(indexDocument);
    }
}