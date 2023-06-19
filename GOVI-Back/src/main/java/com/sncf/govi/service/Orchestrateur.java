package com.sncf.govi.service;

import com.sncf.govi.controller.model.DemandeGOVI;
import com.sncf.govi.service.model.Conducteur;
import com.sncf.govi.service.model.Gare;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Orchestrateur {
    private List<Gare> listGares;
    private List<Conducteur> listConducteurs;

    private final NettoyeurDonnees nettoyeurDonnees;
    private final LecteurFichier lecteurFichier;
    private final InitialiseGare initialiseGare;
    private final CreateurDonnees createurDonnees;
    private final AffecteurDonnees affecteurDonnees;


    public List<Gare> generationGOVI(DemandeGOVI demandeGOVI){
        listGares = initialiseGare.creationGares();
        /*lecteurFichier();
        nettoyeurDonnees();
        createurDonnees();
        affecteurDonnees();*/

        return(listGares);
    }
}
