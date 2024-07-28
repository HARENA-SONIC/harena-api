package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.mapper.PossesionMapper;
import com.harena.api.endpoint.rest.model.ListPayload;
import com.harena.api.endpoint.rest.model.Possession;
import com.harena.api.service.PossessionService;
import com.harena.api.utils.Page;
import com.harena.api.utils.PageRequest;
import com.harena.api.utils.Pageable;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patrimoines/{nom_patrimoine}/possessions")
public class PossessionController {
  private final PossessionService possessionService;
  private final PossesionMapper possesionMapper;

  @GetMapping
  public Page<Possession> getPatrimoinePossessions(
      @PathVariable(name = "nom_patrimoine") String nomPatrimoine,
      @RequestParam("page") int pageNumber,
      @RequestParam("page_size") int pageSize) {
    Optional<List<school.hei.patrimoine.modele.possession.Possession>> possessions =
        possessionService.getPossessions(nomPatrimoine);

    if (possessions.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    List<Possession> possessionList =
        possessions.get().stream().map(possesionMapper::toRestModel).toList();
    Pageable<Possession> possessionPageable = new Pageable<>(possessionList);

    return possessionPageable.getPage(PageRequest.of(pageNumber, pageSize));
  }

  @PutMapping
  public List<Possession> crupdatePatrimoinePossessions(
      @PathVariable(name = "nom_patrimoine") String nomPatrimoine,
      @RequestBody ListPayload<Possession> toSavePossessions) {
    return possessionService
        .savePossessions(
            nomPatrimoine,
            toSavePossessions.data().stream().map(possesionMapper::toObjectModel).toList())
        .stream()
        .map(possesionMapper::toRestModel)
        .toList();
  }

  @GetMapping("/{nom_possession}")
  public Possession getPossessionPatrimoineByNom(
      @PathVariable(name = "nom_patrimoine") String nomPatrimoine,
      @PathVariable(name = "nom_possession") String nomPossession) {
    Optional<Possession> fetchedPossession =
        possessionService
            .getPossession(nomPatrimoine, nomPossession)
            .map(possesionMapper::toRestModel);

    return fetchedPossession.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @DeleteMapping("/{nom_possession}")
  public Possession removePossessionByNom(
      @PathVariable("nom_patrimoine") String nomPatrimoine,
      @PathVariable("nom_possession") String nomPossession) {
    return possessionService
        .removePossession(nomPatrimoine, nomPossession)
        .map(possesionMapper::toRestModel)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }
}
