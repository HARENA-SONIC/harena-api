package com.harena.api.endpoint.rest.mapper;

import static java.util.Objects.requireNonNull;
import static school.hei.patrimoine.modele.Devise.*;

import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.Devise;

@Component
class DeviseMapper implements Mapper<Devise, com.harena.api.endpoint.rest.model.Devise> {
  @Override
  public com.harena.api.endpoint.rest.model.Devise toRestModel(Devise objectModel) {
    return new com.harena.api.endpoint.rest.model.Devise()
        .nom(objectModel.nom())
        .code(getRestCodeValue(objectModel));
  }

  @Override
  public Devise toObjectModel(com.harena.api.endpoint.rest.model.Devise restDevise) {
    return switch (requireNonNull(restDevise.getCode())) {
      case "MGA" -> MGA;
      case "EUR" -> Devise.EUR;
      case "CAD" -> Devise.CAD;
      default -> Devise.NON_NOMMEE;
    };
  }

  private String getRestCodeValue(Devise objectDevise) {
    if (objectDevise.equals(MGA)) {
      return "MGA";
    } else if (objectDevise.equals(EUR)) {
      return "EUR";
    } else if (objectDevise.equals(CAD)) {
      return "CAD";
    } else {
      return "NON_NOMMEE";
    }
  }
}
