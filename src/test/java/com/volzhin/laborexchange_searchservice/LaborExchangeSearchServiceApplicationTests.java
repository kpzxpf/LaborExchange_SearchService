package com.volzhin.laborexchange_searchservice;

import com.volzhin.laborexchange_searchservice.repository.ResumeElasticRepository;
import com.volzhin.laborexchange_searchservice.repository.VacancyElasticRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LaborExchangeSearchServiceApplicationTests {

    @MockBean
    VacancyElasticRepository vacancyElasticRepository;

    @MockBean
    ResumeElasticRepository resumeElasticRepository;

    @Test
    void contextLoads() {
    }

}
