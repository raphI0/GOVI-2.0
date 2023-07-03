package com.sncf.govi.service;

import com.sncf.govi.service.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AffecteurDonnees {

    public List<Gare> affectation (Mission missionArrivee, Mission missionDepart, Retournement retournement, List<Gare> gares, String quai, HashMap<String, ConducteurContainer> conducteursParMission){

        retournement.getMissionsDepart().add(missionDepart);
        retournement.getMissionsArrivee().add(missionArrivee);
        retournement.setCouleur(CouleurEnum.CARBONE);

        return(affecterRetournement(retournement, gares, quai));
    }

    /**
     * Attribue un conducteur à une mission.
     *
     * @param mission la mission à laquelle attribuer un conducteur
     * @param conducteursParMission Hashmap des conducteurs sur chaque mission
     * @return la mission avec un conducteur attribué
     */
    Mission affecterConducteurAMission(Mission mission, HashMap<String, ConducteurContainer> conducteursParMission){
        if(conducteursParMission.get(mission.getCodeMission()) != null) {
            mission.setConducteurTrain(conducteursParMission.get(mission.getCodeMission()).getConducteurTrain());
            mission.setConducteursEVLoc(conducteursParMission.get(mission.getCodeMission()).getConducteursEVLoc());
        }
        return mission;
    }

    /**
     * Affecte un retournement à la bonne gare et le bon quai
     * @param retournement
     * @param gares
     * @param idQuai
     * @return
     */
    private List<Gare> affecterRetournement(Retournement retournement, List<Gare> gares, String idQuai){
        // On parcourt toutes les gares et quais
        for(Gare gare : gares){
            // Si l'alias de la gare de départ correspond
            if(gare.getAlias().equals(retournement.getMissionsArrivee().get(0).getGareArrivee()) | gare.getAlias().equals(retournement.getMissionsDepart().get(0).getGareDepart())){
                for(Quai quai : gare.getQuais()){
                    // Si l'ID du quai correspond
                    if(quai.getId().equals(idQuai)){
                        // On crée une liste de retournements vide si elle n'existe pas
                        if(quai.getRetournements() == null){
                            quai.setRetournements(new ArrayList<>());
                        }
                        quai.getRetournements().add(retournement);
                    }
                }
            }
        }
        return gares;
    }
}
