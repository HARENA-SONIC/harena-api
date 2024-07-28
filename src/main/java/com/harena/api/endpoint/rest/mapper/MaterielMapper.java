package com.harena.api.endpoint.rest.mapper;

import static java.util.Objects.requireNonNullElse;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import school.hei.patrimoine.modele.possession.Materiel;

@Component
@RequiredArgsConstructor
class MaterielMapper implements Mapper<Materiel, com.harena.api.endpoint.rest.model.Materiel> {
  private final DeviseMapper deviseMapper;

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
    return new com.harena.api.endpoint.rest.model.Materiel()
        .nom(objectModel.getNom())
        .t(objectModel.getT())
        .valeurComptable(objectModel.getValeurComptable())
        .devise(deviseMapper.toRestModel(objectModel.getDevise()))
        // The worst things ! cause field: dateAcquisition and tauxDAppreciationAnnuelle don't have
        // getter
        .dateDAcquisition((LocalDate) getPrivateFieldValue("dateAcquisition", objectModel))
        .tauxDappreciationAnnuel(
            (Double) getPrivateFieldValue("tauxDAppreciationAnnuelle", objectModel));
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
