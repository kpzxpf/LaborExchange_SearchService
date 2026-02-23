package com.volzhin.laborexchange_searchservice.service.index;

import com.volzhin.laborexchange_searchservice.dto.VacancyIndexEvent;
import com.volzhin.laborexchange_searchservice.mapper.VacancyMapper;
import com.volzhin.laborexchange_searchservice.repository.VacancyElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexingVacancyService {

    private final VacancyElasticRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;

    public void indexVacancy(VacancyIndexEvent event) {
        log.info("Indexing vacancy id={} title={}", event.getId(), event.getTitle());
        vacancyRepository.save(vacancyMapper.toIndex(event));
    }
}