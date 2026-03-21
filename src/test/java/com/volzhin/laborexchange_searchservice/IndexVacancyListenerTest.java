package com.volzhin.laborexchange_searchservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volzhin.laborexchange_searchservice.dto.VacancyIndexEvent;
import com.volzhin.laborexchange_searchservice.listener.IndexVacancyListener;
import com.volzhin.laborexchange_searchservice.service.index.IndexingVacancyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IndexVacancyListenerTest {

    @Mock
    private IndexingVacancyService indexingVacancyService;

    @Spy
    private ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private IndexVacancyListener listener;

    @Test
    void listen_validJson_callsIndexService() {
        String json = "{\"id\":1,\"title\":\"Java Developer\",\"description\":\"Backend\"," +
                "\"companyName\":\"ACME\",\"location\":\"Moscow\",\"skills\":[\"java\",\"spring\"]}";

        listener.listen(json);

        verify(indexingVacancyService, times(1)).indexVacancy(any(VacancyIndexEvent.class));
    }

    @Test
    void listen_invalidJson_doesNotCallIndexService() {
        listener.listen("{ not valid json !!!");

        verify(indexingVacancyService, never()).indexVacancy(any());
    }

    @Test
    void listen_emptyJson_doesNotCallIndexService() {
        listener.listen("");

        verify(indexingVacancyService, never()).indexVacancy(any());
    }

    @Test
    void listen_validJson_parsesEventCorrectly() {
        String json = "{\"id\":42,\"title\":\"Senior Dev\",\"skills\":[\"kotlin\"]}";

        listener.listen(json);

        verify(indexingVacancyService).indexVacancy(argThat(event ->
                event.getId().equals(42L) && event.getTitle().equals("Senior Dev")
        ));
    }
}
