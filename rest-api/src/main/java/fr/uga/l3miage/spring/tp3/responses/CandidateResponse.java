package fr.uga.l3miage.spring.tp3.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidateResponse {
    private Double average;
}
