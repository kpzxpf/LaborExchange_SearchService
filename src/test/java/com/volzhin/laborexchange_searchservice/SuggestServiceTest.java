package com.volzhin.laborexchange_searchservice;

import com.volzhin.laborexchange_searchservice.dto.SuggestResponse;
import com.volzhin.laborexchange_searchservice.service.SuggestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuggestServiceTest {

    @Mock
    ElasticsearchOperations elasticsearchOperations;

    @InjectMocks
    SuggestService service;

    @Test
    void suggest_shortQuery_returnsEmpty() {
        SuggestResponse result = service.suggest("j", "vacancy");
        assertThat(result.getSuggestions()).isEmpty();
        assertThat(result.getQuery()).isEqualTo("j");
    }

    @Test
    void suggest_nullQuery_returnsEmpty() {
        SuggestResponse result = service.suggest(null, "vacancy");
        assertThat(result.getSuggestions()).isEmpty();
    }

    @Test
    void suggest_elasticsearchError_returnsEmpty() {
        when(elasticsearchOperations.search(any(), any())).thenThrow(new RuntimeException("ES error"));
        SuggestResponse result = service.suggest("java", "vacancy");
        // graceful degradation — ES error must not propagate to caller
        assertThat(result.getSuggestions()).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    void suggest_validVacancyType_returnsResponse() {
        SearchHits mockHits = mock(SearchHits.class);
        when(mockHits.getSearchHits()).thenReturn(List.of());
        when(elasticsearchOperations.search(any(), any())).thenReturn(mockHits);

        SuggestResponse result = service.suggest("java", "vacancy");

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("vacancy");
        assertThat(result.getQuery()).isEqualTo("java");
        assertThat(result.getSuggestions()).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    void suggest_locationType_queriesBothIndexes() {
        SearchHits mockHits = mock(SearchHits.class);
        when(mockHits.getSearchHits()).thenReturn(List.of());
        // location type triggers two ES calls (vacancies + resumes)
        when(elasticsearchOperations.search(any(), any())).thenReturn(mockHits);

        SuggestResponse result = service.suggest("mo", "location");

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("location");
    }

    @Test
    @SuppressWarnings("unchecked")
    void suggest_skillType_returnsResponse() {
        SearchHits mockHits = mock(SearchHits.class);
        when(mockHits.getSearchHits()).thenReturn(List.of());
        when(elasticsearchOperations.search(any(), any())).thenReturn(mockHits);

        SuggestResponse result = service.suggest("sp", "skill");

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("skill");
    }

    @Test
    @SuppressWarnings("unchecked")
    void suggest_unknownType_fallsBackToVacancyTitle() {
        SearchHits mockHits = mock(SearchHits.class);
        when(mockHits.getSearchHits()).thenReturn(List.of());
        when(elasticsearchOperations.search(any(), any())).thenReturn(mockHits);

        SuggestResponse result = service.suggest("dev", "unknown");

        assertThat(result).isNotNull();
        assertThat(result.getType()).isEqualTo("unknown");
        assertThat(result.getSuggestions()).isEmpty();
    }
}
