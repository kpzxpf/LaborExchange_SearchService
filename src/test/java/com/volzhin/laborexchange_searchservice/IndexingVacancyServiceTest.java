package com.volzhin.laborexchange_searchservice;

import com.volzhin.laborexchange_searchservice.dto.VacancyIndexEvent;
import com.volzhin.laborexchange_searchservice.entity.VacancyIndex;
import com.volzhin.laborexchange_searchservice.mapper.VacancyMapper;
import com.volzhin.laborexchange_searchservice.repository.VacancyElasticRepository;
import com.volzhin.laborexchange_searchservice.service.index.IndexingVacancyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndexingVacancyServiceTest {

    @Mock
    private VacancyElasticRepository vacancyRepository;

    @Mock
    private VacancyMapper vacancyMapper;

    @InjectMocks
    private IndexingVacancyService indexingVacancyService;

    @Test
    void indexVacancy_savesDocumentToElasticsearch() {
        VacancyIndexEvent event = VacancyIndexEvent.builder()
                .id(1L)
                .title("Java Developer")
                .description("Spring Boot experience required")
                .companyName("TechCorp")
                .location("Moscow")
                .skills(Set.of("Java", "Spring"))
                .build();

        VacancyIndex index = VacancyIndex.builder()
                .id("1")
                .title("Java Developer")
                .description("Spring Boot experience required")
                .companyName("TechCorp")
                .location("Moscow")
                .skills(Set.of("Java", "Spring"))
                .build();

        when(vacancyMapper.toIndex(event)).thenReturn(index);
        when(vacancyRepository.save(any(VacancyIndex.class))).thenReturn(index);

        indexingVacancyService.indexVacancy(event);

        verify(vacancyMapper).toIndex(event);
        verify(vacancyRepository).save(index);
    }
}
