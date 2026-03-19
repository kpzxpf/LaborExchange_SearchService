package com.volzhin.laborexchange_searchservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Search Service API")
                        .version("1.0.0")
                        .description("""
                                Elasticsearch-powered full-text search for vacancies and resumes in LaborExchange.

                                **Index population:** Indexes are kept up to date by consuming Kafka topics:
                                - `indexing-vacancy` — published by VacancyService
                                - `indexing-resume` — published by ResumeService

                                **Query strategy:**
                                - `query` field uses multi-match with fuzziness (`AUTO`) and field boosting
                                - `skills` filter requires ALL specified skills to be present (AND logic)
                                - Numeric filters (`salary`, `experienceYears`) use range queries
                                - Results are sorted by relevance score (`_score` DESC)

                                **Analyzers:** Russian morphological analyzer is applied to title, description, summary, and institutions fields.
                                """)
                        .contact(new Contact().name("LaborExchange Team"))
                        .license(new License().name("MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8087").description("Direct"),
                        new Server().url("http://localhost:8080").description("Via API Gateway")))
                .tags(List.of(
                        new Tag().name("Vacancy Search").description("Full-text and filtered vacancy search"),
                        new Tag().name("Resume Search").description("Full-text and filtered resume search")));
    }
}
