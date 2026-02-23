package com.volzhin.laborexchange_searchservice.mapper;

import com.volzhin.laborexchange_searchservice.dto.VacancyIndexEvent;
import com.volzhin.laborexchange_searchservice.dto.VacancySearchResponse;
import com.volzhin.laborexchange_searchservice.entity.VacancyIndex;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(target = "id", expression = "java(event.getId().toString())")
    VacancyIndex toIndex(VacancyIndexEvent event);

    VacancySearchResponse toResponse(VacancyIndex index);
}