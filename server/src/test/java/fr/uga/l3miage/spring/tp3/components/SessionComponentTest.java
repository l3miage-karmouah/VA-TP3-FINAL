package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationStepEntity;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationStepRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SessionComponentTest {
    @Autowired
    private SessionComponent sessionComponent;


    @Test
    void testCreateSession(){
        //GIVEN
        EcosSessionProgrammationStepEntity ecosSessionProgrammationStepEntity = EcosSessionProgrammationStepEntity
                .builder()
                .description("test")
                .dateTime(LocalDateTime.now())
                .build();

        EcosSessionProgrammationEntity ecosSessionProgrammationEntity = EcosSessionProgrammationEntity
                .builder()
                .ecosSessionProgrammationStepEntities(Set.of(ecosSessionProgrammationStepEntity))
                .build();

        EcosSessionEntity ecosSessionEntity = EcosSessionEntity
                .builder()
                .id(12L)
                .ecosSessionProgrammationEntity(ecosSessionProgrammationEntity)
                .build();


        //WHEN
        EcosSessionEntity sessionEntityResponse = sessionComponent.createSession(ecosSessionEntity);

        //THEN
        assertThat(sessionEntityResponse).usingRecursiveComparison().isEqualTo(sessionEntityResponse);
    }


}
