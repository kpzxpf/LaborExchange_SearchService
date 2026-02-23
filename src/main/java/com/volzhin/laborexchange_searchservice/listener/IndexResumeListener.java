package com.volzhin.laborexchange_searchservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volzhin.laborexchange_searchservice.dto.ResumeIndexEvent;
import com.volzhin.laborexchange_searchservice.service.index.IndexingResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IndexResumeListener {
    private final IndexingResumeService indexingResumeService;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "${spring.kafka.topics.indexing-resume}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        try {
            ResumeIndexEvent event = mapper.readValue(message, ResumeIndexEvent.class);
            log.info("Event received: {}", event);

            indexingResumeService.indexResume(event);
        } catch (Exception e) {
            log.error("JSON parsing error", e);
        }
    }
}
