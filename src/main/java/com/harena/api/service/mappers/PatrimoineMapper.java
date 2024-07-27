package com.harena.api.service.mappers;

import java.util.Set;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;

@Component
public class PatrimoineMapper
    implements Mapper<Patrimoine, com.harena.api.endpoint.rest.model.Patrimoine> {

  @Override
  public Patrimoine toObjectModel(com.harena.api.endpoint.rest.model.Patrimoine restModel) {
    Personne possesseur = new Personne(restModel.getPossesseur().getNom());

    return new Patrimoine(restModel.getNom(), possesseur, restModel.getT(), Set.of());
  }

  @Override
  public com.harena.api.endpoint.rest.model.Patrimoine toRestModel(Patrimoine objectModel) {
    return new com.harena.api.endpoint.rest.model.Patrimoine()
        .nom(objectModel.nom())
        .possesseur(
            new com.harena.api.endpoint.rest.model.Personne().nom(objectModel.possesseur().nom()))
        .t(objectModel.t())
        .valeurComptable(objectModel.getValeurComptable());
  }
}
