package com.harena.api.service.mappers;

import static com.harena.api.endpoint.rest.model.Argent.TypeEnum.*;
import static java.util.Objects.requireNonNullElse;

import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.Creance;
import school.hei.patrimoine.modele.possession.Dette;

@Component
public class ArgentMapper implements Mapper<Argent, com.harena.api.endpoint.rest.model.Argent> {
  @Override
  public com.harena.api.endpoint.rest.model.Argent toRestModel(Argent objectModel) {
    var argent = new com.harena.api.endpoint.rest.model.Argent();
    argent.setNom(objectModel.getNom());
    argent.setT(objectModel.getT());
    argent.setValeurComptable(objectModel.getValeurComptable());
    argent.setDateDOuverture(argent.getDateDOuverture());
    if (objectModel instanceof Creance) {
      argent.setType(CREANCE);
    } else if (objectModel instanceof Dette) {
      argent.setType(DETTE);
    } else {
      argent.setType(AUTRES);
    }
    var deviseMapper = new DeviseMapper();
    argent.setDevise(deviseMapper.toRestModel(objectModel.getDevise()));
    return argent;
  }

  @Override
  public Argent toObjectModel(com.harena.api.endpoint.rest.model.Argent restModel) {
    var type = restModel.getType();
    var name = restModel.getNom();
    var tTime = restModel.getT();
    int comptableValue = requireNonNullElse(restModel.getValeurComptable(), 0);
    var devise = new DeviseMapper().toObjectModel(restModel.getDevise());
    if (type == DETTE) {
      return new Dette(name, tTime, comptableValue, devise);
    } else if (type == CREANCE) {
      return new Creance(name, tTime, comptableValue, devise);
    }
    return new Argent(name, tTime, comptableValue, devise);
  }
}
