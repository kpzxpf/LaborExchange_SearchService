package com.volzhin.laborexchange_searchservice.mapper;

import com.volzhin.laborexchange_searchservice.dto.ResumeIndexEvent;
import com.volzhin.laborexchange_searchservice.dto.ResumeSearchResponse;
import com.volzhin.laborexchange_searchservice.entity.ResumeIndex;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResumeMapper {

    ResumeIndex toIndex(ResumeIndexEvent event);

    ResumeSearchResponse toResponse(ResumeIndex index);
}
