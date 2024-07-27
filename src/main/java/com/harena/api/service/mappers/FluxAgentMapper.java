package com.harena.api.service.mappers;

import school.hei.patrimoine.modele.possession.FluxArgent;

class FluxAgentMapper implements Mapper<FluxArgent, com.harena.api.endpoint.rest.model.FluxArgent> {
  @Override
  public com.harena.api.endpoint.rest.model.FluxArgent toRestModel(FluxArgent objectModel) {
    var fluxArgent = new com.harena.api.endpoint.rest.model.FluxArgent();
    fluxArgent.setNom(objectModel.getNom());
    fluxArgent.setT(objectModel.getT());
    fluxArgent.setDebut(objectModel.getDebut());
    fluxArgent.setFin(objectModel.getFin());
    fluxArgent.setDevise(new DeviseMapper().toRestModel(objectModel.getDevise()));
    fluxArgent.setArgent(new ArgentMapper().toRestModel(objectModel.getArgent()));
    fluxArgent.setDateDOperation(objectModel.getDateOperation());
    fluxArgent.setValeurComptable(objectModel.getValeurComptable());
    fluxArgent.setFluxMensuel(objectModel.getFluxMensuel());
    return fluxArgent;
  }

  @Override
  public FluxArgent toObjectModel(com.harena.api.endpoint.rest.model.FluxArgent restModel) {
    return new FluxArgent(
        restModel.getNom(),
        new ArgentMapper().toObjectModel(restModel.getArgent()),
        restModel.getDebut(),
        restModel.getFin(),
        restModel.getFluxMensuel(),
        restModel.getDateDOperation(),
        new DeviseMapper().toObjectModel(restModel.getDevise()));
  }
}
