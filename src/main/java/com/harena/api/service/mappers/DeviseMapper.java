package com.harena.api.service.mappers;

import school.hei.patrimoine.modele.Devise;

class DeviseMapper implements Mapper<Devise, com.harena.api.endpoint.rest.model.Devise> {
  @Override
  public com.harena.api.endpoint.rest.model.Devise toRestModel(Devise objectModel) {
    var devise = new com.harena.api.endpoint.rest.model.Devise();
    devise.setNom(objectModel.nom());
    if (objectModel == Devise.MGA) {
      devise.setCode("MGA");
    } else if (objectModel == Devise.CAD) {
      devise.setCode("CAD");
    } else if (objectModel == Devise.EUR) {
      devise.setCode("EUR");
    } else {
      devise.setCode("Unknown");
    }
    return devise;
  }

  @Override
  public Devise toObjectModel(com.harena.api.endpoint.rest.model.Devise restModel) {
    var name = restModel.getNom();
    return switch (name) {
      case "MGA" -> Devise.MGA;
      case "EUR" -> Devise.EUR;
      case "CAD" -> Devise.CAD;
      default -> Devise.NON_NOMMEE;
    };
  }
}
