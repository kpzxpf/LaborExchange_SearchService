package com.volzhin.laborexchange_searchservice.filter.resume;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchRequest;
import com.volzhin.laborexchange_searchservice.filter.QueryFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Component
public class SkillsResumeFilter implements QueryFilter<ResumeSearchRequest> {

    @Override
    public Optional<Query> apply(ResumeSearchRequest request) {
        if (CollectionUtils.isEmpty(request.getSkills())) {
            return Optional.empty();
        }

        List<Query> skillTerms = request.getSkills().stream()
                .map(skill -> Query.of(q -> q
                        .term(t -> t.field("skills").value(skill.toLowerCase()))
                ))
                .toList();

        return Optional.of(Query.of(q -> q
                .bool(BoolQuery.of(b -> b.must(skillTerms)))
        ));
    }
}