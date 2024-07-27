package com.harena.api.endpoint.rest.patrimoine;

import com.harena.api.endpoint.rest.model.FluxImpossibles;
import com.harena.api.service.ProjectionFutureService;
import com.harena.api.service.mappers.FluxImpossibleMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patrimoines/{nom_patrimoine}")
public class ProjectionFutureController {
  private final ProjectionFutureService projectionFutureService;
  private final FluxImpossibleMapper mapper;

  @GetMapping("flux-impossibles")
  public List<FluxImpossibles> fluxImpossibles(
      @PathVariable("nom_patrimoine") String nomPatrimoine,
      @RequestParam LocalDate debut,
      @RequestParam LocalDate fin) {
    return projectionFutureService.getFluxImpossibles(nomPatrimoine, debut, fin).stream()
        .map(mapper::toRestModel)
        .toList();
  }

  @GetMapping(value = "graphe", produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] getGraphe(
      @PathVariable("nom_patrimoine") String nomPatrimoine,
      @RequestParam LocalDate debut,
      @RequestParam LocalDate fin) {
    try {
      return Files.readAllBytes(
          projectionFutureService.getGraph(nomPatrimoine, debut, fin).toPath());
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
