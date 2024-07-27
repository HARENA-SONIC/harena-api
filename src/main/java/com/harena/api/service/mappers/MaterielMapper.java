package com.harena.api.service.mappers;

import static java.util.Objects.requireNonNullElse;

import java.time.LocalDate;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.possession.Materiel;

@Component
class MaterielMapper implements Mapper<Materiel, com.harena.api.endpoint.rest.model.Materiel> {
  @SneakyThrows
  private static Object getPrivateFieldValue(String fieldName, Object instance) {
    var field = Materiel.class.getDeclaredField(fieldName);
    field.setAccessible(true);
    var value = field.get(instance);
    field.setAccessible(false);
    return value;
  }

  @Override
  public com.harena.api.endpoint.rest.model.Materiel toRestModel(Materiel objectModel) {
    var material = new com.harena.api.endpoint.rest.model.Materiel();
    material.setNom(objectModel.getNom());
    material.setT(objectModel.getT());
    material.setValeurComptable(objectModel.getValeurComptable());
    material.setDevise(new DeviseMapper().toRestModel(objectModel.getDevise()));

    // The worst things ! cause field: dateAcquisition and tauxDAppreciationAnnuelle don't have
    // getter
    var dateAcquisition = (LocalDate) getPrivateFieldValue("dateAcquisition", objectModel);
    var tauxDAppreciationAnnuelle =
        (Double) getPrivateFieldValue("tauxDAppreciationAnnuelle", objectModel);

    material.setDateDAcquisition(dateAcquisition);
    material.setTauxDappreciationAnnuel(tauxDAppreciationAnnuelle);
    return material;
  }

  @Override
  public Materiel toObjectModel(com.harena.api.endpoint.rest.model.Materiel restModel) {
    var nom = restModel.getNom();
    var tTime = restModel.getT();
    int comptableValue = requireNonNullElse(restModel.getValeurComptable(), 0);
    var dateDAcquisition = restModel.getDateDAcquisition();
    double tauxAppreciationAnnuelle =
        requireNonNullElse(restModel.getTauxDappreciationAnnuel(), 0.);
    var restDevise = restModel.getDevise();
    if (restDevise == null) {
      return new Materiel(nom, tTime, comptableValue, dateDAcquisition, tauxAppreciationAnnuelle);
    }
    var devise = new DeviseMapper().toObjectModel(restDevise);
    return new Materiel(
        nom, tTime, comptableValue, dateDAcquisition, tauxAppreciationAnnuelle, devise);
  }
}
