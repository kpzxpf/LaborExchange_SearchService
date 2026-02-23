package com.volzhin.laborexchange_searchservice.filter;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.Optional;


public interface QueryFilter<R> {
    Optional<Query> apply(R request);
}
