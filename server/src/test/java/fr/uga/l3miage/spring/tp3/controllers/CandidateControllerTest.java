package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.responses.CandidateResponse;
import fr.uga.l3miage.spring.tp3.services.CandidateService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private CandidateRepository candidateRepository;


    @AfterEach
    public void clear() {
        candidateRepository.deleteAll();
    }

    @Test
    void testGetAverageCandidateFound(){
        //GIVEN
        final HttpHeaders headers = new HttpHeaders();
        //when(candidateService.getCandidateAverage(12L)).thenReturn(13.32);
        //GIVEN
        // création des exams
        ExamEntity examEntity1 = ExamEntity.builder().weight(2).build();
        ExamEntity examEntity2 = ExamEntity.builder().weight(1).build();
        ExamEntity examEntity3 = ExamEntity.builder().weight(4).build();
        // création des grilles d'évaluation
        CandidateEvaluationGridEntity candidateEvaluationGrid1 = CandidateEvaluationGridEntity
                .builder()
                .grade(12)
                .examEntity(examEntity1)
                .build();
        CandidateEvaluationGridEntity candidateEvaluationGrid2 = CandidateEvaluationGridEntity
                .builder()
                .grade(14)
                .examEntity(examEntity2)
                .build();
        CandidateEvaluationGridEntity candidateEvaluationGrid3 = CandidateEvaluationGridEntity
                .builder()
                .grade(11)
                .examEntity(examEntity3)
                .build();
        // création du candidat
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .id(27L)
                .email("manal.ifegh@gmail.com")
                .candidateEvaluationGridEntities(Set.of(candidateEvaluationGrid1, candidateEvaluationGrid2, candidateEvaluationGrid3))
                .build();

        candidateRepository.save(candidateEntity);

        //WHEN
        ResponseEntity<CandidateResponse> response = testRestTemplate.exchange("/api/candidates/27/average", HttpMethod.GET, new HttpEntity<>(null, headers), CandidateResponse.class);

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


    @Test
    void testGetAverageCandidateNotFound(){
        //GIVEN
        final HttpHeaders headers = new HttpHeaders();
        // on n'ajoute aucun candidat

        //WHEN
        ResponseEntity<CandidateNotFoundRestException> response = testRestTemplate.exchange("/api/candidates/1/average", HttpMethod.GET, new HttpEntity<>(null, headers), CandidateNotFoundRestException.class);

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);


    }

}
