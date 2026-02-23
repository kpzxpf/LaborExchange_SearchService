package com.volzhin.laborexchange_searchservice.service.search;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.volzhin.laborexchange_searchservice.filter.QueryFilter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticQueryBuilder {

    public <R> Query build(R request,
                           List<QueryFilter<R>> mustFilters,
                           List<QueryFilter<R>> filterFilters) {

        List<Query> mustQueries = mustFilters.stream()
                .map(f -> f.apply(request))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .toList();

        List<Query> filterQueries = filterFilters.stream()
                .map(f -> f.apply(request))
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .toList();

        if (mustQueries.isEmpty() && filterQueries.isEmpty()) {
            return Query.of(q -> q.matchAll(m -> m));
        }

        return Query.of(q -> q.bool(BoolQuery.of(b -> b
                .must(mustQueries)
                .filter(filterQueries)
        )));
    }
}