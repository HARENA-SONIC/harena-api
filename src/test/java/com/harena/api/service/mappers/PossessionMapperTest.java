package com.harena.api.service.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.harena.api.endpoint.rest.model.Devise;
import com.harena.api.endpoint.rest.model.Possession;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;

public class PossessionMapperTest {
  private final PossesionMapper mapper = new PossesionMapper();

  @Test
  public void to_rest_model_flux_argent_test() {
    var now = LocalDate.now();
    var model =
        new FluxArgent(
            "test flux argent",
            new Argent("money for test", now, 4000),
            now,
            now.plusDays(2),
            -2000,
            10);

    var argent =
        new com.harena.api.endpoint.rest.model.Argent()
            .type(com.harena.api.endpoint.rest.model.Argent.TypeEnum.AUTRES)
            .nom("money for test")
            .valeurComptable(4000)
            .devise(new Devise().nom("non-nommee").code("NON_NOMMEE"))
            .t(now);

    var fluxArgent =
        new com.harena.api.endpoint.rest.model.FluxArgent()
            .nom("test flux argent")
            .argent(argent)
            .debut(now)
            .fin(now.plusDays(2))
            .valeurComptable(0)
            .fluxMensuel(-2000)
            .devise(new Devise().nom("non-nommee").code("NON_NOMMEE"))
            .dateDOperation(10);

    var expected = new Possession().type(Possession.TypeEnum.FLUXARGENT).fluxArgent(fluxArgent);
    assertEquals(expected, mapper.toRestModel(model));
  }

  @Test
  public void to_object_flux_argent_model_test() {
    var now = LocalDate.now();

    var model =
        new Possession()
            .type(Possession.TypeEnum.FLUXARGENT)
            .fluxArgent(
                new com.harena.api.endpoint.rest.model.FluxArgent()
                    .nom("test flux argent")
                    .argent(
                        new com.harena.api.endpoint.rest.model.Argent()
                            .nom("money for test")
                            .t(now.plusDays(2))
                            .valeurComptable(400)
                            .devise(new Devise().nom("non-nommee").code("NON_NOMMEE")))
                    .debut(now)
                    .fin(now.plusDays(5))
                    .fluxMensuel(-2500)
                    .dateDOperation(20)
                    .devise(new Devise().nom("non-nommee").code("NON_NOMMEE")));

    var expected =
        new FluxArgent(
            "test flux argent",
            new Argent("money for test", now, 4000),
            now,
            now.plusDays(5),
            -2500,
            20);
    assertEquals(expected, mapper.toObjectModel(model));
  }
}
