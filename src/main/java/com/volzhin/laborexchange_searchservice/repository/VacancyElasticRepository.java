package com.volzhin.laborexchange_searchservice.repository;

import com.volzhin.laborexchange_searchservice.entity.VacancyIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyElasticRepository extends ElasticsearchRepository<VacancyIndex, String> {
}