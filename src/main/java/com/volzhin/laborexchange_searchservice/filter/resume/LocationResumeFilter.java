package com.volzhin.laborexchange_searchservice.filter.resume;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchRequest;
import com.volzhin.laborexchange_searchservice.filter.QueryFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class LocationResumeFilter implements QueryFilter<ResumeSearchRequest> {

    @Override
    public Optional<Query> apply(ResumeSearchRequest request) {
        if (!StringUtils.hasText(request.getLocation())) {
            return Optional.empty();
        }

        return Optional.of(Query.of(q -> q
                .term(t -> t.field("location").value(request.getLocation()))
        ));
    }
}
