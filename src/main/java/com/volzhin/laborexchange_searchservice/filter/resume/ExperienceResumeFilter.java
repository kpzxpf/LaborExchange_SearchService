package com.volzhin.laborexchange_searchservice.filter.resume;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchRequest;
import com.volzhin.laborexchange_searchservice.filter.QueryFilter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ExperienceResumeFilter implements QueryFilter<ResumeSearchRequest> {

    @Override
    public Optional<Query> apply(ResumeSearchRequest request) {
        if (request.getExperienceYearsMin() == null && request.getExperienceYearsMax() == null) {
            return Optional.empty();
        }

        return Optional.of(Query.of(q -> q
                .range(r -> {
                    r.field("experienceYears");
                    if (request.getExperienceYearsMin() != null) r.gte(JsonData.of(request.getExperienceYearsMin()));
                    if (request.getExperienceYearsMax() != null) r.lte(JsonData.of(request.getExperienceYearsMax()));
                    return r;
                })
        ));
    }
}