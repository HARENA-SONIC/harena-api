package com.harena.api.endpoint.rest.mapper;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.FluxImpossibles;

@Component
@RequiredArgsConstructor
public class FluxImpossibleMapper
    implements Mapper<FluxImpossibles, com.harena.api.endpoint.rest.model.FluxImpossibles> {
  private final FluxAgentMapper fluxAgentMapper;

  @Override
  public com.harena.api.endpoint.rest.model.FluxImpossibles toRestModel(
      FluxImpossibles objectModel) {
    return new com.harena.api.endpoint.rest.model.FluxImpossibles()
        .fluxArgents(objectModel.flux().stream().map(fluxAgentMapper::toRestModel).toList())
        .date(objectModel.date())
        .nomArgent(objectModel.nomArgent())
        .valeurArgent(objectModel.valeurArgent());
  }

  @Override
  public FluxImpossibles toObjectModel(
      com.harena.api.endpoint.rest.model.FluxImpossibles restModel) {
    return new FluxImpossibles(
        restModel.getDate(),
        restModel.getNomArgent(),
        requireNonNull(restModel.getValeurArgent()),
        requireNonNull(restModel.getFluxArgents()).stream()
            .map(fluxAgentMapper::toObjectModel)
            .collect(Collectors.toSet()));
  }
}
