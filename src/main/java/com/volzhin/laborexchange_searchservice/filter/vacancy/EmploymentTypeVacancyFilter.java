package com.volzhin.laborexchange_searchservice.filter.vacancy;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchRequest;
import com.volzhin.laborexchange_searchservice.filter.QueryFilter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmploymentTypeVacancyFilter implements QueryFilter<VacancySearchRequest> {

    @Override
    public Optional<Query> apply(VacancySearchRequest request) {
        if (request.getEmploymentType() == null || request.getEmploymentType().isBlank()) {
            return Optional.empty();
        }
        return Optional.of(Query.of(q -> q
                .term(t -> t.field("employmentType").value(request.getEmploymentType().toUpperCase()))
        ));
    }
}
