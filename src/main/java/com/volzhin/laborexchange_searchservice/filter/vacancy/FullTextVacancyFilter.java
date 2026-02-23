package com.volzhin.laborexchange_searchservice.filter.vacancy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchRequest;
import com.volzhin.laborexchange_searchservice.filter.QueryFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class FullTextVacancyFilter implements QueryFilter<VacancySearchRequest> {

    @Override
    public Optional<Query> apply(VacancySearchRequest request) {
        if (!StringUtils.hasText(request.getQuery())) {
            return Optional.empty();
        }

        return Optional.of(Query.of(q -> q
                .multiMatch(mm -> mm
                        .query(request.getQuery())
                        .fields("title^3", "description^1", "companyName^2")
                        .fuzziness("AUTO")
                        .prefixLength(2)
                )
        ));
    }
}