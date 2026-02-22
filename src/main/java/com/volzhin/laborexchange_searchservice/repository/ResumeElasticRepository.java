package com.volzhin.laborexchange_searchservice.repository;

import com.volzhin.laborexchange_searchservice.entity.ResumeIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeElasticRepository extends ElasticsearchRepository<ResumeIndex, String> {
}
