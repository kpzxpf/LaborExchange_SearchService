package com.volzhin.laborexchange_searchservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;
import java.util.Set;

@Document(indexName = "vacancies")
@Setting(settingPath = "elasticsearch/vacancy-settings.json")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyIndex {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String title;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String description;

    @Field(type = FieldType.Double)
    private Double salary;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String companyName;

    @Field(type = FieldType.Keyword)
    private String location;

    @Field(type = FieldType.Keyword, normalizer = "lowercase_normalizer")
    private Set<String> skills;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdAt;
}