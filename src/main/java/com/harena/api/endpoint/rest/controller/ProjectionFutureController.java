package com.harena.api.endpoint.rest.controller;

import com.harena.api.endpoint.rest.mapper.FluxImpossibleMapper;
import com.harena.api.endpoint.rest.model.FluxImpossibles;
import com.harena.api.exception.InternalServerErrorException;
import com.harena.api.service.ProjectionFutureService;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
      throw new InternalServerErrorException();
    }
  }
}
