package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateRepositoryTest {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private TestCenterRepository testCenterRepository;
    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;

    @BeforeEach
    void clear(){
        candidateRepository.deleteAll();
        testCenterRepository.deleteAll();
        candidateEvaluationGridRepository.deleteAll();
    }

    @Test
    void testRequestFindAllByTestCenterEntityCode(){
        //GIVEN
        // création des testCenterEntities
        TestCenterEntity testCenterEntity1 = TestCenterEntity
                .builder()
                .code(TestCenterCode.GRE)
                .build();

        TestCenterEntity testCenterEntity2 = TestCenterEntity
                .builder()
                .code(TestCenterCode.DIJ)
                .build();

        TestCenterEntity testCenterEntity3 = TestCenterEntity
                .builder()
                .code(TestCenterCode.GRE)
                .build();

        testCenterRepository.save(testCenterEntity1);
        testCenterRepository.save(testCenterEntity2);
        testCenterRepository.save(testCenterEntity3);


        // création des CandidateEntities
        CandidateEntity candidateEntity1 = CandidateEntity
                .builder()
                .firstname("Ahmed")
                .lastname("Karmous")
                .email("ahmed.karmous@gmail.com")
                .birthDate(LocalDate.of(2002, 12, 12))
                .hasExtraTime(false)
                .testCenterEntity(testCenterEntity1)
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("Manal")
                .lastname("Ifegh")
                .email("manal.ifegh@gmail.com")
                .birthDate(LocalDate.of(2004, 10, 27))
                .hasExtraTime(true)
                .testCenterEntity(testCenterEntity2)
                .build();


        CandidateEntity candidateEntity3 = CandidateEntity
                .builder()
                .firstname("Ghita")
                .lastname("Mouden")
                .email("ghita.mouden@gmail.com")
                .birthDate(LocalDate.of(2002, 2, 9))
                .hasExtraTime(false)
                .testCenterEntity(testCenterEntity3)
                .build();


        candidateRepository.save(candidateEntity1);
        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity3);


        //WHEN
        Set<CandidateEntity> candidateEntitiesResponseGrenoble = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.GRE);
        // une requête qui renvoie un Set vide
        Set<CandidateEntity> candidateEntitiesResponseParis = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.PAR);

        //THEN
        //on doit trouver deux éléments dont le code du Test Center est GRE
        assertThat(candidateEntitiesResponseGrenoble).hasSize(2);
        assertThat(candidateEntitiesResponseGrenoble.stream().findAny().get().getTestCenterEntity().getCode()).isEqualTo(TestCenterCode.GRE);

        assertThat(candidateEntitiesResponseParis).hasSize(0);
    }






    @Test
    void testRequestFindAllByCandidateEvaluationGridEntitiesGradeLessThan(){
        //GIVEN
        // création des CandidateEntities
        CandidateEntity candidateEntity1 = CandidateEntity
                .builder()
                .firstname("Ahmed")
                .lastname("Karmous")
                .email("ahmed.karmous@gmail.com")
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("Manal")
                .lastname("Ifegh")
                .email("manal.ifegh@gmail.com")
                .build();

        candidateRepository.save(candidateEntity1);
        candidateRepository.save(candidateEntity2);


        // création des candidateEvaluationGridEntities pour le premier candidat
        CandidateEvaluationGridEntity candidateEvaluationGridEntity11 = CandidateEvaluationGridEntity
                .builder()
                .grade(16.75)
                .candidateEntity(candidateEntity1)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity12 = CandidateEvaluationGridEntity
                .builder()
                .grade(8)
                .candidateEntity(candidateEntity1)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity13 = CandidateEvaluationGridEntity
                .builder()
                .grade(12.25)
                .candidateEntity(candidateEntity1)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity14 = CandidateEvaluationGridEntity
                .builder()
                .grade(14)
                .candidateEntity(candidateEntity1)
                .build();


        // création des candidateEvaluationGridEntities pour le deuxième candidat
        CandidateEvaluationGridEntity candidateEvaluationGridEntity21 = CandidateEvaluationGridEntity
                .builder()
                .grade(10.75)
                .candidateEntity(candidateEntity2)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity22 = CandidateEvaluationGridEntity
                .builder()
                .grade(12)
                .candidateEntity(candidateEntity2)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity23 = CandidateEvaluationGridEntity
                .builder()
                .grade(15.25)
                .candidateEntity(candidateEntity2)
                .build();

        CandidateEvaluationGridEntity candidateEvaluationGridEntity24 = CandidateEvaluationGridEntity
                .builder()
                .grade(20)
                .candidateEntity(candidateEntity2)
                .build();

        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity11);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity12);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity13);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity14);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity21);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity22);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity23);
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity24);



        //WHEN
        Set<CandidateEntity> candidateEntitiesResponseFound = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10);
        //une requête qui renvoie un Set vide
        Set<CandidateEntity> candidateEntitiesResponseNotFound = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(5);

        // THEN
        assertThat(candidateEntitiesResponseFound).hasSize(1);
        assertThat(candidateEntitiesResponseFound.stream().findFirst().get().getId()).isEqualTo(candidateEntity1.getId());
        assertThat(candidateEntitiesResponseNotFound).hasSize(0);

    }

    @Test
    void testRequestFindAllByHasExtraTimeFalseAndBirthDateBefore(){
        //GIVEN
        // création des CandidateEntities
        CandidateEntity candidateEntity1 = CandidateEntity
                .builder()
                .firstname("Ahmed")
                .lastname("Karmous")
                .email("ahmed.karmous@gmail.com")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2002, 12, 12))
                .build();

        CandidateEntity candidateEntity2 = CandidateEntity
                .builder()
                .firstname("Manal")
                .lastname("Ifegh")
                .email("manal.ifegh@gmail.com")
                .hasExtraTime(true)
                .birthDate(LocalDate.of(2004, 10, 27))
                .build();

        CandidateEntity candidateEntity3 = CandidateEntity
                .builder()
                .firstname("Ghita")
                .lastname("Mouden")
                .email("ghita.mouden@gmail.com")
                .hasExtraTime(false)
                .birthDate(LocalDate.of(2002, 2, 9))
                .build();

        candidateRepository.save(candidateEntity1);
        candidateRepository.save(candidateEntity2);
        candidateRepository.save(candidateEntity3);


        //WHEN
        Set<CandidateEntity> candidateEntitiesResponseFound = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(2002, 12, 13));
        //une requête qui renvoie un Set vide
        Set<CandidateEntity> candidateEntitiesResponseNotFound = candidateRepository.findAllByHasExtraTimeFalseAndBirthDateBefore(LocalDate.of(1975, 1, 1));

        // THEN
        assertThat(candidateEntitiesResponseFound).hasSize(2);
        assertThat(candidateEntitiesResponseFound.stream().findFirst().get().getId()).isEqualTo(candidateEntity1.getId());
        assertThat(candidateEntitiesResponseNotFound).hasSize(0);
    }

}
