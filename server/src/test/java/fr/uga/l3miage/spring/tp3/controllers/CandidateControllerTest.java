package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
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
    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;
    @Autowired
    private ExamRepository examRepository;


    @BeforeEach
    void clear(){
        candidateRepository.deleteAll();
        examRepository.deleteAll();
        candidateEvaluationGridRepository.deleteAll();
    }

    @Test
    void testGetAverageCandidateFound(){
        //GIVEN
        final HttpHeaders headers = new HttpHeaders();
        //GIVEN
        // création du candidat
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .id(27L)
                .email("manal.ifegh@gmail.com")
                .build();
        candidateRepository.save(candidateEntity);
        // création des exams
        ExamEntity examEntity1 = ExamEntity.builder().weight(2).build();
        ExamEntity examEntity2 = ExamEntity.builder().weight(1).build();
        ExamEntity examEntity3 = ExamEntity.builder().weight(4).build();
        examRepository.save(examEntity1);
        examRepository.save(examEntity2);
        examRepository.save(examEntity3);

        // création des grilles d'évaluation
        CandidateEvaluationGridEntity candidateEvaluationGrid1 = CandidateEvaluationGridEntity
                .builder()
                .grade(12)
                .examEntity(examEntity1)
                .candidateEntity(candidateEntity)
                .build();
        candidateEvaluationGridRepository.save(candidateEvaluationGrid1);
        CandidateEvaluationGridEntity candidateEvaluationGrid2 = CandidateEvaluationGridEntity
                .builder()
                .grade(14)
                .examEntity(examEntity2)
                .candidateEntity(candidateEntity)
                .build();
        candidateEvaluationGridRepository.save(candidateEvaluationGrid2);
        CandidateEvaluationGridEntity candidateEvaluationGrid3 = CandidateEvaluationGridEntity
                .builder()
                .grade(11)
                .examEntity(examEntity3)
                .candidateEntity(candidateEntity)
                .build();
        candidateEvaluationGridRepository.save(candidateEvaluationGrid3);


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
