package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CreationSessionRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.ExamNotFoundException;
import fr.uga.l3miage.spring.tp3.mappers.ExamMapper;
import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationStepEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionServiceTest {
    @Autowired
    private SessionService sessionService;
    @MockBean
    private ExamComponent examComponent;
    @MockBean
    private SessionComponent sessionComponent;
    @SpyBean
    private SessionMapper sessionMapper;


    @Test
    void testCreateSessionExamNotFound(){
      //GIVEN
      SessionProgrammationCreationRequest sessionProgrammationCreationRequest = SessionProgrammationCreationRequest
                .builder()
                .steps(Set.of())
                .build();

        SessionCreationRequest sessionCreationRequest = SessionCreationRequest
                .builder()
                .examsId(Set.of(1L, 2L, 3L))
                .ecosSessionProgrammation(sessionProgrammationCreationRequest)
                .build();

        EcosSessionEntity ecosSessionEntity = sessionMapper.toEntity(sessionCreationRequest);
        when(sessionComponent.createSession(ecosSessionEntity)).thenReturn(ecosSessionEntity);
        try{
            when(examComponent.getAllById(Set.of(1L, 2L, 3L))).thenThrow(ExamNotFoundException.class);
        } catch (ExamNotFoundException e){
            //
        }

        //WHEN - THEN
        assertThrows(CreationSessionRestException.class, ()->sessionService.createSession(sessionCreationRequest));
    }



    @Test
    void testCreateSessionExamsFound(){
        //GIVEN
        ExamEntity examEntity1 = ExamEntity.builder().build();
        ExamEntity examEntity2 = ExamEntity.builder().build();
        ExamEntity examEntity3 = ExamEntity.builder().build();


        SessionProgrammationCreationRequest sessionProgrammationCreationRequest = SessionProgrammationCreationRequest
                .builder()
                .steps(Set.of())
                .build();

        SessionCreationRequest sessionCreationRequest = SessionCreationRequest
                .builder()
                .examsId(Set.of(1L, 2L, 3L))
                .ecosSessionProgrammation(sessionProgrammationCreationRequest)
                .build();

        EcosSessionEntity ecosSessionEntity = sessionMapper.toEntity(sessionCreationRequest);
        when(sessionComponent.createSession(ecosSessionEntity)).thenReturn(ecosSessionEntity);
        try{
            when(examComponent.getAllById(Set.of(1L, 2L, 3L))).thenReturn(Set.of(examEntity1, examEntity2, examEntity3));
        } catch (ExamNotFoundException e){
            //
        }

        //WHEN - THEN
        assertDoesNotThrow(()->sessionService.createSession(sessionCreationRequest));
        verify(sessionMapper, times(2)).toEntity(sessionCreationRequest);

    }


}
