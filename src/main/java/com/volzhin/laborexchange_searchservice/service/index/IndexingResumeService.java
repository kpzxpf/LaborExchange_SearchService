package com.volzhin.laborexchange_searchservice.service.index;

import com.volzhin.laborexchange_searchservice.dto.ResumeIndexEvent;
import com.volzhin.laborexchange_searchservice.mapper.ResumeMapper;
import com.volzhin.laborexchange_searchservice.repository.ResumeElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexingResumeService {

    private final ResumeElasticRepository resumeRepository;
    private final ResumeMapper resumeMapper;

    public void indexResume(ResumeIndexEvent event) {
        log.info("Indexing resume id={} title={}", event.getId(), event.getTitle());
        resumeRepository.save(resumeMapper.toIndex(event));
    }
}
