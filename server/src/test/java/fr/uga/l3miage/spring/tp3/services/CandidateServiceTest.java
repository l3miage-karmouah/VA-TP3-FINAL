package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateServiceTest {
    @Autowired
    CandidateService candidateService;
    @MockBean
    CandidateComponent candidateComponent;


    @Test
    void testGetCandidateAverageFound() {
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
        //C'est un mock, la méthode ne va pas lancer une exception mais il faut la gérer
        try {
            when(candidateComponent.getCandidatById(27L)).thenReturn(candidateEntity);
        } catch (CandidateNotFoundException e){
            //
        }

        //WHEN - THEN
        // il n'y a pas d'expetions levées car le candidat est forcément trouvé (on a fait un MOCK)
        assertThat(candidateService.getCandidateAverage(27L)).isEqualTo(82d/7);
    }




    @Test
    void testGetCandidateAverageNotFound(){
        //GIVEN
        try {
            when(candidateComponent.getCandidatById(27L)).thenThrow(CandidateNotFoundException.class);
        } catch (CandidateNotFoundException e){
            //
        }

        // WHEN - THEN
        assertThrows(CandidateNotFoundRestException.class, ()->candidateService.getCandidateAverage(27L));
    }



}
