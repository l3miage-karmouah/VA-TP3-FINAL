package fr.uga.l3miage.spring.tp3.component;


import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
        public class CandidateComponentTest {
        @Autowired
        private CandidateComponent candidateComponent;



}
