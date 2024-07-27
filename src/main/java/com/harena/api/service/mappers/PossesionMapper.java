package com.harena.api.service.mappers;

import static com.harena.api.endpoint.rest.model.Possession.TypeEnum.*;

import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

public class PossesionMapper
    implements Mapper<Possession, com.harena.api.endpoint.rest.model.Possession> {
  @Override
  public com.harena.api.endpoint.rest.model.Possession toRestModel(Possession objectModel) {
    var possession = new com.harena.api.endpoint.rest.model.Possession();
    if (objectModel instanceof FluxArgent fluxArgent) {
      possession.setType(FLUXARGENT);
      var flux = new FluxAgentMapper().toRestModel(fluxArgent);
      possession.setFluxArgent(flux);
      return possession;
    } else if (objectModel instanceof Materiel materiel) {
      possession.setType(MATERIEL);
      var material = new MaterielMapper().toRestModel(materiel);
      possession.setMateriel(material);
    } else if (objectModel instanceof Argent argent) {
      possession.setType(ARGENT);
      var money = new ArgentMapper().toRestModel(argent);
      possession.setArgent(money);
    }
    return possession;
  }

  @Override
  public Possession toObjectModel(com.harena.api.endpoint.rest.model.Possession restModel) {
    var type = restModel.getType();
    if (type == FLUXARGENT) {
      var fluxArgent = restModel.getFluxArgent();
      return new FluxAgentMapper().toObjectModel(fluxArgent);
    } else if (type == ARGENT) {
      var material = restModel.getArgent();
      return new ArgentMapper().toObjectModel(material);
    }
    var material = restModel.getMateriel();
    return new MaterielMapper().toObjectModel(material);
  }
}
