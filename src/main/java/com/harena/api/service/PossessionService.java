package com.harena.api.service;

import com.harena.api.utils.StringNormalizer;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.possession.Possession;

@Service
@RequiredArgsConstructor
public class PossessionService {
  private final PatrimoineService patrimoineService;

  public Optional<List<Possession>> getPossessions(String patrimoineName) {
    String patrimoineId = StringNormalizer.apply(patrimoineName);
    Optional<Patrimoine> patrimoine = patrimoineService.getPatrimone(patrimoineId);

    return patrimoine.map(value -> value.possessions().stream().toList());
  }

  public Optional<Possession> getPossession(String patrimoineName, String possessionName) {
    String patrimoineId = StringNormalizer.apply(patrimoineName);
    String possessionId = StringNormalizer.apply(possessionName);
    Optional<Patrimoine> patrimoine = patrimoineService.getPatrimone(patrimoineId);
    if (patrimoine.isEmpty()) {
      return Optional.empty();
    }
    HashMap<String, Possession> possessions = new HashMap<>();
    patrimoine
        .get()
        .possessions()
        .forEach(
            possession -> possessions.put(StringNormalizer.apply(possession.getNom()), possession));
    return Optional.ofNullable(possessions.get(possessionId));
  }

  public List<Possession> savePossessions(
      String patrimoineName, List<Possession> toSavePossessions) {
    Optional<Patrimoine> patrimoine =
        patrimoineService.getPatrimone(StringNormalizer.apply(patrimoineName));
    if (patrimoine.isEmpty()) {
      return List.of();
    }

    HashMap<String, Possession> possessions = retrievePossessions(patrimoine.get());
    toSavePossessions.forEach(
        possession -> possessions.put(StringNormalizer.apply(possession.getNom()), possession));
    Patrimoine updatedPatrimoine =
        new Patrimoine(
            patrimoine.get().nom(),
            patrimoine.get().possesseur(),
            patrimoine.get().t(),
            new HashSet<>(possessions.values()));

    patrimoineService.savePatrimoine(List.of(updatedPatrimoine));
    return toSavePossessions;
  }

  public Optional<Possession> removePossession(String patrimoineName, String possessionName) {
    Optional<Patrimoine> patrimoine = patrimoineService.getPatrimone(patrimoineName);
    if (patrimoine.isEmpty()) {
      return Optional.empty();
    }

    HashMap<String, Possession> possessions = retrievePossessions(patrimoine.get());
    return Optional.ofNullable(possessions.remove(StringNormalizer.apply(possessionName)));
  }

  private HashMap<String, Possession> retrievePossessions(Patrimoine patrimoine) {
    HashMap<String, Possession> possessions = new HashMap<>();
    patrimoine
        .possessions()
        .forEach(
            possession -> possessions.put(StringNormalizer.apply(possession.getNom()), possession));
    return possessions;
  }
}
