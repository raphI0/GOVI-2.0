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
import org.springframework.web.multipart.MultipartFile;
import java.time.temporal.ChronoUnit;

import java.time.LocalDateTime;
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

    private final FichierLu fichierLu = FichierLu.builder().build();

    public void readFile(MultipartFile file, TypeFichierEnum typeFichier){
        this.lecteurFichier.reader(file, typeFichier, fichierLu);
        log.info("File Read !");
        if(fichierLu.getBhlj1() != null
                && fichierLu.getBhlj2() != null
                && fichierLu.getRatp() != null
                && fichierLu.getPacific1() != null
                && fichierLu.getPacific2() != null){
            log.info("All files read !");
        }
    }

    public List<Gare> generationGOVI(DemandeGOVI demandeGOVI){
        // Création des gares qui doivent être remplis de nos retournements
        this.listGares = garesProvider.getGares();

        // Modification de la date pour qu'elle ne contienne que le jour, pas les heures
        if (demandeGOVI.getDate() != null) {
            demandeGOVI.setDate(demandeGOVI.getDate().truncatedTo(ChronoUnit.DAYS));
        }

        // Fusion des fichiers J et J+1
        //fichierLu.setBhl(nettoyeurDonnees.fusionJ1J2(fichierLu.getBhlj1(),fichierLu.getBhlj2()));
        //fichierLu.setPacific(nettoyeurDonnees.fusionJ1J2(fichierLu.getPacific1(),fichierLu.getPacific2()));

        // Création de nos données conducteur via la lecture des fichiers pacific
        createurDonnees.creationConducteurs(fichierLu.getPacific1());
        if(fichierLu.getPacific2() != null){
            createurDonnees.creationConducteurs(fichierLu.getPacific2());
        }

        // Création de nos retournements (et affectation quai/gare) via la lecture des fichiers BHL
        this.listGares = createurDonnees.creationRetournementBHL(fichierLu.getBhlj1(), demandeGOVI.getDate(), this.listGares, false);
        if(fichierLu.getBhlj2() != null) {
            this.listGares = createurDonnees.creationRetournementBHL(fichierLu.getBhlj2(), demandeGOVI.getDate(), this.listGares, true);
        }

        // Création de nos retournements (et affectation quai/gare) via la lecture du fichier RATP
        if(fichierLu.getRatp() != null) {
            this.listGares = createurDonnees.creationRetournementRATP(fichierLu.getRatp(),demandeGOVI.getDate(),this.listGares);
        }

        return(listGares);
    }
}
