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

        // Fusion des fichiers J et J+1
        //fichierLu.setBhl(nettoyeurDonnees.fusionJ1J2(fichierLu.getBhlj1(),fichierLu.getBhlj2()));
        //fichierLu.setPacific(nettoyeurDonnees.fusionJ1J2(fichierLu.getPacific1(),fichierLu.getPacific2()));
        createurDonnees.creationConducteurs(fichierLu.getPacific1());
        if(fichierLu.getPacific2() != null){
            createurDonnees.creationConducteurs(fichierLu.getPacific2());
        }
        this.listGares = createurDonnees.creationRetournement(fichierLu.getBhlj1(),TypeFichierEnum.BHL.name(), demandeGOVI.getDate(), this.listGares, false);
        if(fichierLu.getBhlj2() != null) {
            this.listGares = createurDonnees.creationRetournement(fichierLu.getBhlj2(), TypeFichierEnum.BHL.name(), demandeGOVI.getDate(), this.listGares, true);
        }
        /*createurDonnees();
        affecteurDonnees();*/

        return(listGares);
    }
}
