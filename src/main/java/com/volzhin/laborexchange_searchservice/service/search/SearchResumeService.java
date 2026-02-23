package com.volzhin.laborexchange_searchservice.service.search;

import com.volzhin.laborexchange_searchservice.dto.PageResponse;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchRequest;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchResponse;
import com.volzhin.laborexchange_searchservice.entity.ResumeIndex;
import com.volzhin.laborexchange_searchservice.filter.resume.ExperienceResumeFilter;
import com.volzhin.laborexchange_searchservice.filter.resume.FullTextResumeFilter;
import com.volzhin.laborexchange_searchservice.filter.resume.SkillsResumeFilter;
import com.volzhin.laborexchange_searchservice.mapper.ResumeMapper;
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
public class SearchResumeService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQueryBuilder queryBuilder;
    private final PageResponseFactory pageResponseFactory;
    private final ResumeMapper resumeMapper;

    private final FullTextResumeFilter fullTextFilter;
    private final SkillsResumeFilter skillsFilter;
    private final ExperienceResumeFilter experienceFilter;

    public PageResponse<ResumeSearchResponse> search(ResumeSearchRequest request) {
        log.debug("Searching resumes: {}", request);

        NativeQuery query = NativeQuery.builder()
                .withQuery(queryBuilder.build(
                        request,
                        List.of(fullTextFilter),
                        List.of(skillsFilter, experienceFilter)
                ))
                .withPageable(PageRequest.of(request.getPage(), request.getSize()))
                .withSort(Sort.by(Sort.Direction.DESC, "_score"))
                .build();

        SearchHits<ResumeIndex> hits = elasticsearchOperations.search(query, ResumeIndex.class);

        List<ResumeSearchResponse> content = hits.getSearchHits().stream()
                .map(hit -> resumeMapper.toResponse(hit.getContent()))
                .toList();

        return pageResponseFactory.build(content, hits.getTotalHits(), request.getPage(), request.getSize());
    }
}