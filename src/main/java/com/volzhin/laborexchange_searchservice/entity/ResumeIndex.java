package com.volzhin.laborexchange_searchservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.Set;

@Document(indexName = "resumes")
@Setting(settingPath = "elasticsearch/resume-settings.json")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeIndex {
    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String title;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String summary;

    @Field(type = FieldType.Integer)
    private Integer experienceYears;

    @Field(type = FieldType.Keyword, normalizer = "lowercase_normalizer")
    private Set<String> skills;

    @Field(type = FieldType.Text, analyzer = "russian")
    private Set<String> institutions;
}