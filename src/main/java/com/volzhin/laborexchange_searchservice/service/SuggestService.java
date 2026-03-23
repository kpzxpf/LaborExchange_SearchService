package com.volzhin.laborexchange_searchservice.service;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.volzhin.laborexchange_searchservice.dto.SuggestResponse;
import com.volzhin.laborexchange_searchservice.entity.ResumeIndex;
import com.volzhin.laborexchange_searchservice.entity.VacancyIndex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class SuggestService {

    private final ElasticsearchOperations elasticsearchOperations;

    private static final int MAX_SUGGESTIONS = 10;
    private static final int FETCH_SIZE = 50;

    /**
     * Returns autocomplete suggestions for given query and type.
     * Uses prefix match on the relevant Keyword field for fast, relevant results.
     *
     * @param q    User input (prefix to match)
     * @param type One of: vacancy (title), company, location, skill
     * @return Up to 10 deduplicated suggestions
     */
    public SuggestResponse suggest(String q, String type) {
        if (q == null || q.trim().length() < 2) {
            return SuggestResponse.builder().query(q).type(type).suggestions(List.of()).build();
        }
        String prefix = q.trim().toLowerCase();
        List<String> suggestions = switch (type) {
            case "company"  -> suggestFromField(prefix, "companyName", VacancyIndex.class, VacancyIndex::getCompanyName);
            case "location" -> suggestLocations(prefix);
            case "skill"    -> suggestSkills(prefix);
            default         -> suggestFromField(prefix, "title", VacancyIndex.class, VacancyIndex::getTitle);
        };
        return SuggestResponse.builder()
                .query(q)
                .type(type)
                .suggestions(suggestions)
                .build();
    }

    /**
     * Combines location suggestions from both vacancies and resumes indexes.
     * location is a Keyword field in both indexes, so prefix query works directly.
     */
    private List<String> suggestLocations(String prefix) {
        List<String> vacancyLocs = suggestFromField(prefix, "location", VacancyIndex.class, VacancyIndex::getLocation);
        List<String> resumeLocs  = suggestFromField(prefix, "location", ResumeIndex.class, ResumeIndex::getLocation);
        return Stream.concat(vacancyLocs.stream(), resumeLocs.stream())
                .distinct()
                .sorted()
                .limit(MAX_SUGGESTIONS)
                .collect(Collectors.toList());
    }

    /**
     * Suggests skill names from the vacancies index.
     * skills is a Keyword (Set<String>) field with lowercase_normalizer,
     * so a flat prefix query on the field works — no nested path needed.
     */
    private List<String> suggestSkills(String prefix) {
        NativeQuery nq = NativeQuery.builder()
                .withQuery(Query.of(q -> q
                        .prefix(p -> p.field("skills").value(prefix))
                ))
                .withMaxResults(FETCH_SIZE)
                .withSourceFilter(new FetchSourceFilter(new String[]{"skills"}, null))
                .build();
        try {
            SearchHits<VacancyIndex> hits = elasticsearchOperations.search(nq, VacancyIndex.class);
            return hits.getSearchHits().stream()
                    .map(hit -> hit.getContent().getSkills())
                    .filter(Objects::nonNull)
                    .flatMap(Set::stream)
                    .filter(s -> s != null && s.toLowerCase().startsWith(prefix))
                    .distinct()
                    .sorted()
                    .limit(MAX_SUGGESTIONS)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Skill suggestions query failed for prefix '{}': {}", prefix, e.getMessage());
            return List.of();
        }
    }

    /**
     * Generic prefix-based suggestion from a single Keyword or Text field.
     * Fetches FETCH_SIZE documents and extracts the field value from each hit.
     */
    private <T> List<String> suggestFromField(String prefix, String fieldName, Class<T> clazz,
                                               Function<T, String> valueExtractor) {
        NativeQuery nq = NativeQuery.builder()
                .withQuery(Query.of(q -> q
                        .prefix(p -> p.field(fieldName).value(prefix))
                ))
                .withMaxResults(FETCH_SIZE)
                .withSourceFilter(new FetchSourceFilter(new String[]{fieldName}, null))
                .build();
        try {
            SearchHits<T> hits = elasticsearchOperations.search(nq, clazz);
            return hits.getSearchHits().stream()
                    .map(hit -> valueExtractor.apply(hit.getContent()))
                    .filter(Objects::nonNull)
                    .filter(v -> v.toLowerCase().startsWith(prefix))
                    .distinct()
                    .sorted()
                    .limit(MAX_SUGGESTIONS)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Suggestion query failed for field '{}' prefix '{}': {}", fieldName, prefix, e.getMessage());
            return List.of();
        }
    }
}
