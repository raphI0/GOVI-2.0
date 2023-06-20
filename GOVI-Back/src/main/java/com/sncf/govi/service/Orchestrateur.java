package com.sncf.govi.service;

import com.sncf.govi.configuration.GaresProvider;
import com.sncf.govi.controller.model.DemandeGOVI;
import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.model.Conducteur;
import com.sncf.govi.service.model.Gare;
import com.sncf.govi.service.model.fichierlecteur.FichierLu;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class Orchestrateur {
    private List<Gare> listGares;
    private List<Conducteur> listConducteurs;

    private final NettoyeurDonnees nettoyeurDonnees;
    private final LecteurFichier lecteurFichier;
    private final GaresProvider garesProvider;
    private final CreateurDonnees createurDonnees;
    private final AffecteurDonnees affecteurDonnees;

    private final FichierLu fichierLu = FichierLu.builder().build();

    public void readFile(MultipartFile file, TypeFichierEnum typeFichier){
        this.lecteurFichier.reader(file, typeFichier, fichierLu);
        log.info("File Read !");
    }

    public List<Gare> generationGOVI(DemandeGOVI demandeGOVI){
        this.listGares = garesProvider.getGares();
        fichierLu.setBhl(nettoyeurDonnees.fusionJ1J2(fichierLu.getBhlj1(),fichierLu.getBhlj2()));

        /*createurDonnees();
        affecteurDonnees();*/

        return(listGares);
    }
}
