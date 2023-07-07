package com.sncf.govi.service;

import com.sncf.govi.service.model.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AffecteurDonnees {

    /**
     * Remplissage de notre instance de retournement et ajout de celui-ci à notre gare
     * @param missionArrivee entité mission d'arrivée de notre retournement
     * @param missionDepart entité mission de départ de notre retournement
     * @param retournement notre entité à remplir
     * @param gares liste de nos gares, auxquelles ajouter nos retournements
     * @param quai ID du quai sur lequel se déroule le retournement
     * @return la liste de nos gares remplit de notre retournement
     */
    public List<Gare> affectation (Mission missionArrivee, Mission missionDepart, Retournement retournement, List<Gare> gares, String quai){

        retournement.getMissionsDepart().add(missionDepart);
        retournement.getMissionsArrivee().add(missionArrivee);
        retournement.setCouleur(CouleurEnum.CARBONE);

        return(affecterRetournement(retournement, gares, quai));
    }

    /**
     * Attribue un conducteur à une mission, au travers d'une hashmap qui liste les conducteurs sur chaque mission
     *
     * @param mission la mission à laquelle attribuer un conducteur
     * @param conducteursParMission Hashmap des conducteurs et les EV Loc sur chaque mission
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
     * Affecte un retournement à la bonne gare au bon quai
     * @param retournement est l'instance de retournement rempli à affecter
     * @param gares constitue la liste de gares qui existent sur le GOVI
     * @param idQuai est la référence du quai de notre retournement
     * @return donne la nouvelle liste de nos gares, auquel vient désormais s'ajouter notre retournement
     */
    private List<Gare> affecterRetournement(Retournement retournement, List<Gare> gares, String idQuai){

        // On parcourt toutes les gares
        for(Gare gare : gares){

            // Pour chacune, on regarde si l'alias de la gare correspond à celle sur laquelle se trouve le retournement
            if(gare.getAlias().equals(retournement.getMissionsArrivee().get(0).getGareArrivee()) || gare.getAlias().equals(retournement.getMissionsDepart().get(0).getGareDepart())){

                // On parcourt ensuite tous les quais de cette gare
                for(Quai quai : gare.getQuais()){

                    // Pour chaque quai, on regarde si l'ID correspond avec celui du retournement
                    if(quai.getId().equals(idQuai)){

                        // Une fois notre gare et notre quai trouvé, on y ajoute notre retournement et on sort de nos boucles
                        quai.getRetournements().add(retournement);
                        // On retourne ainsi notre liste de gare à laquelle est ajouté notre retournement
                        return gares;
                    }
                }
            }
        }
        // Si notre quai/gare n'a pas été trouvé, on retourne simplement la liste de gare non modifiée
        return gares;
    }
}
