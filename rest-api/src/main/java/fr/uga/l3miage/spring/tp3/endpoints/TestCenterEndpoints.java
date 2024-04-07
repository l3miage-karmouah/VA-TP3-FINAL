package fr.uga.l3miage.spring.tp3.endpoints;

import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.TestCenterRequest;
import fr.uga.l3miage.spring.tp3.responses.TestCenterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Gestion des test centers")
@RestController
@RequestMapping("/api/testcenters")
public interface TestCenterEndpoints {
    @Operation(description = "ajouter des candidats")
    @ApiResponse(responseCode = "202",description = "accepté")
    @ApiResponse(responseCode = "404" ,description = "le centre de test ou l'étudiant n'est pas trouvé", content = @Content(schema = @Schema(implementation = String.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ApiResponse(responseCode = "400" , content = @Content(schema = @Schema(implementation = String.class),mediaType = MediaType.APPLICATION_JSON_VALUE))
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addcandidates")
    TestCenterResponse addCandidates(@RequestBody TestCenterRequest request);
}
