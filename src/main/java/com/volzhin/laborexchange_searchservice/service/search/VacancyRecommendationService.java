package com.volzhin.laborexchange_searchservice.service.search;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.volzhin.laborexchange_searchservice.dto.PageResponse;
import com.volzhin.laborexchange_searchservice.dto.VacancyRecommendationRequest;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchResponse;
import com.volzhin.laborexchange_searchservice.entity.VacancyIndex;
import com.volzhin.laborexchange_searchservice.mapper.VacancyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyRecommendationService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final VacancyMapper vacancyMapper;
    private final PageResponseFactory pageResponseFactory;

    public PageResponse<VacancySearchResponse> recommend(VacancyRecommendationRequest request) {
        log.debug("Generating recommendations for skills: {}", request.getSkills());

        // Each matching skill adds to the relevance score — the more skills match, the higher the vacancy ranks
        List<Query> shouldQueries = request.getSkills().stream()
                .map(skill -> Query.of(q -> q.term(t -> t.field("skills").value(skill.toLowerCase()))))
                .toList();

        List<Query> filterQueries = new ArrayList<>();
        if (request.getLocation() != null && !request.getLocation().isBlank()) {
            filterQueries.add(Query.of(q -> q.term(t -> t.field("location").value(request.getLocation()))));
        }
        if (request.getSalaryMin() != null) {
            filterQueries.add(Query.of(q -> q.range(r -> r.field("salary").gte(JsonData.of(request.getSalaryMin())))));
        }

        List<Query> filters = filterQueries;
        Query query = Query.of(q -> q.bool(BoolQuery.of(b -> {
            b.should(shouldQueries).minimumShouldMatch("1");
            if (!filters.isEmpty()) b.filter(filters);
            return b;
        })));

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(PageRequest.of(request.getPage(), request.getSize()))
                .withSort(Sort.by(Sort.Direction.DESC, "_score"))
                .build();

        SearchHits<VacancyIndex> hits = elasticsearchOperations.search(nativeQuery, VacancyIndex.class);

        List<VacancySearchResponse> content = hits.getSearchHits().stream()
                .map(hit -> vacancyMapper.toResponse(hit.getContent()))
                .toList();

        return pageResponseFactory.build(content, hits.getTotalHits(), request.getPage(), request.getSize());
    }
}
