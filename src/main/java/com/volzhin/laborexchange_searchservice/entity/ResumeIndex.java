package com.volzhin.laborexchange_searchservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Set;

@Document(indexName = "resumes")
@Data
@Builder
public class ResumeIndex {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String title;

    @Field(type = FieldType.Text, analyzer = "russian")
    private String summary;

    @Field(type = FieldType.Integer)
    private Integer experienceYears;

    @Field(type = FieldType.Keyword)
    private Set<String> skills;

    @Field(type = FieldType.Text, analyzer = "russian")
    private Set<String> institutions;
}