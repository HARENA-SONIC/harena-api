package com.harena.api.service.mappers;

import com.harena.api.endpoint.rest.model.Personne;
import com.harena.api.endpoint.rest.model.Possession;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PossessionMapper implements Mapper<Possession, com.harena.api.endpoint.rest.model.Possession> {

    @Override
    public Possession toObjectModel(com.harena.api.endpoint.rest.model.Possession restModel) {
        Personne possesseur = new Personne(restModel.getPossesseur.getNom());
        return new Possession(restModel.getNom(), possesseur, restModel.getT(), Set.of());
    }

    @Override
    public Possession toRestModel(Possession objectModel) {
        return new com.harena.api.endpoint.rest.model.Patrimoine()
                .nom(objectModel.nom())
                .possesseur(
                        new com.harena.api.endpoint.rest.model.Personne().nom(objectModel.possesseur().nom()))
                .t(objectModel.t())
                .valeurComptable(objectModel.getValeurComptable());
    }
}

