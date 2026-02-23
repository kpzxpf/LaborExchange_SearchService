package com.volzhin.laborexchange_searchservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volzhin.laborexchange_searchservice.dto.VacancyIndexEvent;
import com.volzhin.laborexchange_searchservice.service.index.IndexingVacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IndexVacancyListener {
    private final IndexingVacancyService indexingVacancyService;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "${spring.kafka.topics.indexing-vacancy}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(String message) {
        try {
            VacancyIndexEvent event = mapper.readValue(message, VacancyIndexEvent.class);
            log.info("Event received: {}", event);

            indexingVacancyService.indexVacancy(event);
        } catch (Exception e) {
            log.error("JSON parsing error", e);
        }
    }
}
