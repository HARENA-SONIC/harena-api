package com.harena.api.endpoint.rest.mapper;

import static com.harena.api.endpoint.rest.model.Argent.TypeEnum.*;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.Creance;
import school.hei.patrimoine.modele.possession.Dette;

@Component
@RequiredArgsConstructor
public class ArgentMapper implements Mapper<Argent, com.harena.api.endpoint.rest.model.Argent> {
  private final DeviseMapper deviseMapper;

  @Override
  public com.harena.api.endpoint.rest.model.Argent toRestModel(Argent objectModel) {
    return new com.harena.api.endpoint.rest.model.Argent()
        .nom(objectModel.getNom())
        .t(objectModel.getT())
        .valeurComptable(objectModel.getValeurComptable())
        .dateDOuverture(objectModel.getDateOuverture())
        .type(getArgentRestType(objectModel))
        .devise(deviseMapper.toRestModel(objectModel.getDevise()));
  }

  @Override
  public Argent toObjectModel(com.harena.api.endpoint.rest.model.Argent restModel) {
    var type = restModel.getType();
    var name = restModel.getNom();
    var tTime = restModel.getT();
    int comptableValue = requireNonNullElse(restModel.getValeurComptable(), 0);
    var devise = deviseMapper.toObjectModel(requireNonNull(restModel.getDevise()));

    if (type == DETTE) {
      return new Dette(name, tTime, comptableValue, devise);
    } else if (type == CREANCE) {
      return new Creance(name, tTime, comptableValue, devise);
    }
    return new Argent(name, tTime, comptableValue, devise);
  }

  private com.harena.api.endpoint.rest.model.Argent.TypeEnum getArgentRestType(Argent argent) {
    if (argent instanceof Creance) {
      return CREANCE;
    } else if (argent instanceof Dette) {
      return DETTE;
    } else {
      return AUTRES;
    }
  }
}
