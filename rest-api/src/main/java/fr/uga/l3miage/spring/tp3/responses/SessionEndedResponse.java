package fr.uga.l3miage.spring.tp3.responses;

import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class SessionEndedResponse {
    Set<CandidateResponse> candidateResponses;
}
