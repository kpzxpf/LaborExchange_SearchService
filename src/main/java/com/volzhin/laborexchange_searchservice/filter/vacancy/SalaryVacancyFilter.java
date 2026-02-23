package com.volzhin.laborexchange_searchservice.filter.vacancy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchRequest;
import com.volzhin.laborexchange_searchservice.filter.QueryFilter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SalaryVacancyFilter implements QueryFilter<VacancySearchRequest> {

    @Override
    public Optional<Query> apply(VacancySearchRequest request) {
        if (request.getSalaryMin() == null && request.getSalaryMax() == null) {
            return Optional.empty();
        }

        return Optional.of(Query.of(q -> q
                .range(r -> r.untyped(u -> {
                    u.field("salary");
                    if (request.getSalaryMin() != null) u.gte(JsonData.of(request.getSalaryMin()));
                    if (request.getSalaryMax() != null) u.lte(JsonData.of(request.getSalaryMax()));
                    return u;
                }))
        ));
    }
}