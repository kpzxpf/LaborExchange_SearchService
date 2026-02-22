package com.volzhin.laborexchange_searchservice.repository;

import com.volzhin.laborexchange_searchservice.entity.VacancyIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyElasticRepository extends ElasticsearchRepository<VacancyIndex, String> {
    List<VacancyIndex> findByTitle(String title);

    List<VacancyIndex> findByTitleOrDescription(String title, String description);
}