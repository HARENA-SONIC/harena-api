package com.harena.api.endpoint.rest.patrimoine;

import com.harena.api.endpoint.rest.model.Patrimoine;
import com.harena.api.service.PatrimoineService;
import com.harena.api.service.mappers.PatrmoineMapper;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("patrimoines")
public class PatrimoineController {
    private final PatrimoineService patrimoineService;
    private final PatrmoineMapper mapper;

    @PutMapping
    public String crupdatePatrimoines(@RequestBody ListPayload<Patrimoine> entity) {
        List<school.hei.patrimoine.modele.Patrimoine> patrimoines =
                entity.data().stream().map(mapper::toObjectModel).toList();
        patrimoineService.savePatrimoine(patrimoines);
        return "OK";
    }

    @GetMapping
    public ListPayload<Patrimoine> getPatrimoines() {
        return new ListPayload<>(
                patrimoineService.getAllPatrimoine(null).stream().map(mapper::toRestModel).toList());
    }

    @GetMapping("/{nom_patrimoine}")
    public Patrimoine getPatrimoine(@PathVariable(name = "nom_patrimoine") String id) {
        Optional<school.hei.patrimoine.modele.Patrimoine> patrimoine =
                patrimoineService.getPatrimone(id);
        if (patrimoine.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return mapper.toRestModel(patrimoine.get());
    }
}
