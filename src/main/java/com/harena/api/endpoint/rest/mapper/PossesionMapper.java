package com.harena.api.endpoint.rest.mapper;

import static com.harena.api.endpoint.rest.model.Possession.TypeEnum.*;
import static java.util.Objects.requireNonNull;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.possession.Argent;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;
import school.hei.patrimoine.modele.possession.Possession;

@Component
@RequiredArgsConstructor
public class PossesionMapper
    implements Mapper<Possession, com.harena.api.endpoint.rest.model.Possession> {
  private final FluxAgentMapper fluxAgentMapper;
  private final MaterielMapper materielMapper;
  private final ArgentMapper argentMapper;

  @Override
  public com.harena.api.endpoint.rest.model.Possession toRestModel(Possession objectModel) {
    var possession = new com.harena.api.endpoint.rest.model.Possession();
    if (objectModel instanceof FluxArgent fluxArgent) {
      possession.setType(FLUXARGENT);
      var flux = fluxAgentMapper.toRestModel(fluxArgent);
      possession.setFluxArgent(flux);
      return possession;
    } else if (objectModel instanceof Materiel materiel) {
      possession.setType(MATERIEL);
      var material = materielMapper.toRestModel(materiel);
      possession.setMateriel(material);
    } else if (objectModel instanceof Argent argent) {
      possession.setType(ARGENT);
      var money = argentMapper.toRestModel(argent);
      possession.setArgent(money);
    }
    return possession;
  }

  @Override
  public Possession toObjectModel(com.harena.api.endpoint.rest.model.Possession restModel) {
    return switch (requireNonNull(restModel.getType())) {
      case FLUXARGENT -> fluxAgentMapper.toObjectModel(requireNonNull(restModel.getFluxArgent()));
      case MATERIEL -> materielMapper.toObjectModel(requireNonNull(restModel.getMateriel()));
      default -> argentMapper.toObjectModel(requireNonNull(restModel.getArgent()));
    };
  }
}
