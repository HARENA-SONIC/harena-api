package com.harena.api.endpoint.rest.possession;

import com.harena.api.endpoint.rest.model.Possession;
import com.harena.api.endpoint.rest.patrimoine.ListPayload;
import com.harena.api.service.PossessionService;
import com.harena.api.service.mappers.PatrimoineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PossessionController {
    private PossessionService possessionService;
    private PatrimoineMapper mapper;

    @PutMapping("/patrimoines/{patrimoineName}/possessions")
    public String crupsatePossession(@RequestBody ListPayload<Possession> entity,
                                     @PathVariable String patrimoineName) {
        List<school.hei.patrimoine.modele.Possession> possessions = entity.data().stream().map(mapper::toObjectModel).toList();
        possessionService.savePossessions(patrimoineName, possessions);
        return "OK";
    }

    @GetMapping("/patrimoines/{patrimoineName}/possessions")
    public ListPayload<Possession> getPossession() {
        return new ListPayload<>(possessionService.getPossessions(null).stream().map(mapper::toRestModel).toList());
    }

    @GetMapping("/patrimoines/{patrimoineName}/possessions/{possessionName}")
    public Possession getPossession(@PathVariable String patrimoineName, @PathVariable String possessionName) {
        Optional<school.hei.patrimoine.modele.Possession> possession =
                possessionService.getPossession(patrimoineName, possessionName);
        if (possession.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return mapper.toRestModel(possession.get());
    }

}
