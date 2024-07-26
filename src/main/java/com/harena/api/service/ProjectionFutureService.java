package com.harena.api.service;

import com.harena.api.utils.StringNormalizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.EvolutionPatrimoine;
import school.hei.patrimoine.modele.FluxImpossibles;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.visualisation.xchart.GrapheurEvolutionPatrimoine;

import java.io.File;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectionFutureService {
  private final PatrimoineService patrimoineService;

  public Set<FluxImpossibles> getFluxImpossibles(String patrimoineName, LocalDate startDate, LocalDate endDate) {
    Optional<EvolutionPatrimoine> evolutionPatrimoine = getEvolutionPatrimoine(patrimoineName, startDate, endDate);
    if (evolutionPatrimoine.isEmpty()) {
      return Set.of();
    }
    return evolutionPatrimoine.get().getFluxImpossibles();
  }

  public File getGraph(String patrimoineName, LocalDate startDate, LocalDate endDate) {
    Optional<EvolutionPatrimoine> evolutionPatrimoine = getEvolutionPatrimoine(patrimoineName, startDate, endDate);
    return evolutionPatrimoine.map(patrimoine ->
        new GrapheurEvolutionPatrimoine().apply(patrimoine)).orElse(null);
  }

  private Optional<EvolutionPatrimoine> getEvolutionPatrimoine(String patrimoineName, LocalDate startDate, LocalDate endDate) {
    Optional<Patrimoine> patrimoine = patrimoineService.getPatrimone(StringNormalizer.apply(patrimoineName));
    return patrimoine.map(value -> new EvolutionPatrimoine(
        value.nom(),
        value,
        startDate,
        endDate
    ));
  }
}
