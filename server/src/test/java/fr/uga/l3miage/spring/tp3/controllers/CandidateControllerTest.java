package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.services.CandidateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private CandidateService candidateService;

    @Test
    void testGetAverageCandidateFound(){
        //GIVEN
        final HttpHeaders headers = new HttpHeaders();
        when(candidateService.getCandidateAverage(12L)).thenReturn(13.32);

        //WHEN
        ResponseEntity<Double> response = testRestTemplate.exchange("/api/candidates/12/average", HttpMethod.GET, new HttpEntity<>(null, headers), Double.class);

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    void testGetAverageCandidateNotFound(){
        //GIVEN
        final HttpHeaders headers = new HttpHeaders();
        when(candidateService.getCandidateAverage(13L)).thenThrow(CandidateNotFoundRestException.class);


        //WHEN
        ResponseEntity<CandidateNotFoundRestException> response = testRestTemplate.exchange("/api/candidates/13/average", HttpMethod.GET, new HttpEntity<>(null, headers), CandidateNotFoundRestException.class);

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


    }

}
