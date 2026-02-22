package com.volzhin.laborexchange_searchservice.service;


import com.volzhin.laborexchange_searchservice.dto.VacancyIndexEvent;
import com.volzhin.laborexchange_searchservice.entity.VacancyIndex;
import com.volzhin.laborexchange_searchservice.repository.VacancyElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndexingVacancyService {

    private final VacancyElasticRepository vacancyRepository;

    public void indexVacancy(VacancyIndexEvent vacancyIndexEvent) {
        log.info("Indexing vacancy: {}", vacancyIndexEvent.getTitle());

        VacancyIndex indexDocument = VacancyIndex.builder()
                .id(vacancyIndexEvent.getId().toString())
                .title(vacancyIndexEvent.getTitle())
                .description(vacancyIndexEvent.getDescription())
                .companyName(vacancyIndexEvent.getCompanyName())
                .location(vacancyIndexEvent.getLocation())
                .skills(vacancyIndexEvent.getSkills())
                .build();

        vacancyRepository.save(indexDocument);
    }
}