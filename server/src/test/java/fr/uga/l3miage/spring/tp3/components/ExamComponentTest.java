package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.exceptions.technical.ExamNotFoundException;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;

import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ExamComponentTest {
    @Autowired
    private ExamComponent examComponent;
    @Autowired
    private ExamRepository examRepository;
    @Test
    void testExamNotFound(){
        //GIVEN
        ExamEntity examEntity1 = ExamEntity.builder().build();
        ExamEntity examEntity2 = ExamEntity.builder().build();
        examRepository.save(examEntity1);
        examRepository.save(examEntity2);

        //WHEN - THEN
        assertThrows(ExamNotFoundException.class, ()->examComponent.getAllById(Set.of(1L, 3L)));

    }


    @Test
    void testExamsFound(){
        //GIVEN
        ExamEntity examEntity1 = ExamEntity.builder().build();
        ExamEntity examEntity2 = ExamEntity.builder().build();
        ExamEntity examEntity3 = ExamEntity.builder().build();
        examRepository.save(examEntity1);
        examRepository.save(examEntity2);
        examRepository.save(examEntity3);

        //WHEN - THEN
        assertDoesNotThrow(()->examComponent.getAllById(Set.of(1L, 2L, 3L)));
    }
}
