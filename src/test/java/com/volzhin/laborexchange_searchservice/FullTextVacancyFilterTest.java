package com.volzhin.laborexchange_searchservice;

import com.volzhin.laborexchange_searchservice.dto.VacancySearchRequest;
import com.volzhin.laborexchange_searchservice.filter.vacancy.FullTextVacancyFilter;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class FullTextVacancyFilterTest {

    private final FullTextVacancyFilter filter = new FullTextVacancyFilter();

    @Test
    void apply_returnsEmpty_whenQueryIsNull() {
        VacancySearchRequest request = new VacancySearchRequest();
        request.setQuery(null);

        Optional<Query> result = filter.apply(request);

        assertThat(result).isEmpty();
    }

    @Test
    void apply_returnsEmpty_whenQueryIsBlank() {
        VacancySearchRequest request = new VacancySearchRequest();
        request.setQuery("   ");

        Optional<Query> result = filter.apply(request);

        assertThat(result).isEmpty();
    }

    @Test
    void apply_returnsQuery_whenQueryIsPresent() {
        VacancySearchRequest request = new VacancySearchRequest();
        request.setQuery("Java Developer");

        Optional<Query> result = filter.apply(request);

        assertThat(result).isPresent();
    }
}
