package com.harena.api.service.mappers;

import static java.time.Month.JUNE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;

public class PatrimoineMapperTest {
  @Test
  void patrimoine_mapper_test() {
    Mapper<Patrimoine, com.harena.api.endpoint.rest.model.Patrimoine> subject =
        new PatrimoineMapper();
    LocalDate date = LocalDate.of(2024, JUNE, 26);
    Patrimoine patrimoine1 =
        new Patrimoine("Patrimoine 1", new Personne("Personne 1"), date, Set.of());

    com.harena.api.endpoint.rest.model.Patrimoine patrimoine2 =
        new com.harena.api.endpoint.rest.model.Patrimoine()
            .nom(patrimoine1.nom())
            .t(date)
            .possesseur(new com.harena.api.endpoint.rest.model.Personne().nom("Personne 1"))
            .valeurComptable(patrimoine1.getValeurComptable());

    assertEquals(patrimoine2, subject.toRestModel(patrimoine1));
    assertEquals(patrimoine1, subject.toObjectModel(patrimoine2));
  }
}
