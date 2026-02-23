package com.volzhin.laborexchange_searchservice.service.search;

import com.volzhin.laborexchange_searchservice.dto.PageResponse;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchRequest;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchResponse;
import com.volzhin.laborexchange_searchservice.entity.VacancyIndex;
import com.volzhin.laborexchange_searchservice.filter.vacancy.FullTextVacancyFilter;
import com.volzhin.laborexchange_searchservice.filter.vacancy.LocationVacancyFilter;
import com.volzhin.laborexchange_searchservice.filter.vacancy.SalaryVacancyFilter;
import com.volzhin.laborexchange_searchservice.filter.vacancy.SkillsVacancyFilter;
import com.volzhin.laborexchange_searchservice.mapper.VacancyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchVacancyService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQueryBuilder queryBuilder;
    private final PageResponseFactory pageResponseFactory;
    private final VacancyMapper vacancyMapper;
    private final FullTextVacancyFilter fullTextFilter;
    private final SkillsVacancyFilter skillsFilter;
    private final LocationVacancyFilter locationFilter;
    private final SalaryVacancyFilter salaryFilter;

    public PageResponse<VacancySearchResponse> search(VacancySearchRequest request) {
        log.debug("Searching vacancies: {}", request);

        NativeQuery query = NativeQuery.builder()
                .withQuery(queryBuilder.build(
                        request,
                        List.of(fullTextFilter),
                        List.of(skillsFilter, locationFilter, salaryFilter)
                ))
                .withPageable(PageRequest.of(request.getPage(), request.getSize()))
                .withSort(Sort.by(Sort.Direction.DESC, "_score"))
                .build();

        SearchHits<VacancyIndex> hits = elasticsearchOperations.search(query, VacancyIndex.class);

        List<VacancySearchResponse> content = hits.getSearchHits().stream()
                .map(hit -> vacancyMapper.toResponse(hit.getContent()))
                .toList();

        return pageResponseFactory.build(content, hits.getTotalHits(), request.getPage(), request.getSize());
    }
}