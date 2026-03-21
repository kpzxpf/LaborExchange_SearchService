package com.volzhin.laborexchange_searchservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
                                Elasticsearch-powered full-text search for vacancies and resumes.

                                **Index population:** Indexes are kept up to date by consuming Kafka topics:
                                - `indexing-vacancy` — published by VacancyService on create/update/publish
                                - `indexing-resume` — published by ResumeService on create/update/skill change

                                **Query strategy:**
                                - `query` — multi-match with `AUTO` fuzziness, field boosting: title ×3, \
company/institutions ×2
                                - `skills` — AND logic (all specified skills must be present), case-insensitive
                                - Numeric filters (`salary`, `experienceYears`) use inclusive range queries
                                - Results sorted by relevance score (`_score` DESC)

                                **Analyzers:** Russian morphological analyzer applied to text fields.

                                All endpoints are **public** — no authentication required.
                                """)
                        .license(new License().name("MIT")))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT token obtained via POST /api/auth/login")))
                .servers(List.of(
                        new Server().url("http://localhost:8087").description("Direct"),
                        new Server().url("http://localhost:8080").description("Via API Gateway")))
                .tags(List.of(
                        new Tag().name("Vacancy Search").description("Full-text and filtered vacancy search"),
                        new Tag().name("Resume Search").description("Full-text and filtered resume search")));
    }
}
