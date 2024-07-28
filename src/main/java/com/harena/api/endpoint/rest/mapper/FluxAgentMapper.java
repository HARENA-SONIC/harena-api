package com.harena.api.endpoint.rest.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.possession.FluxArgent;

@Component
@RequiredArgsConstructor
class FluxAgentMapper implements Mapper<FluxArgent, com.harena.api.endpoint.rest.model.FluxArgent> {
  private final DeviseMapper deviseMapper;
  private final ArgentMapper argentMapper;

  @Override
  public com.harena.api.endpoint.rest.model.FluxArgent toRestModel(FluxArgent objectModel) {
    return new com.harena.api.endpoint.rest.model.FluxArgent()
        .nom(objectModel.getNom())
        .t(objectModel.getT())
        .debut(objectModel.getDebut())
        .fin(objectModel.getFin())
        .devise(deviseMapper.toRestModel(objectModel.getDevise()))
        .argent(argentMapper.toRestModel(objectModel.getArgent()))
        .dateDOperation(objectModel.getDateOperation())
        .valeurComptable(objectModel.getValeurComptable())
        .fluxMensuel(objectModel.getFluxMensuel());
  }

  @Override
  public FluxArgent toObjectModel(com.harena.api.endpoint.rest.model.FluxArgent restModel) {
    return new FluxArgent(
        restModel.getNom(),
        argentMapper.toObjectModel(restModel.getArgent()),
        restModel.getDebut(),
        restModel.getFin(),
        restModel.getFluxMensuel(),
        restModel.getDateDOperation(),
        new DeviseMapper().toObjectModel(restModel.getDevise()));
  }
}
