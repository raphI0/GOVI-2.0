package com.sncf.govi.service;

import com.sncf.govi.configuration.GaresProvider;
import com.sncf.govi.controller.model.DemandeGOVI;
import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.exception.ExceptionLectureFichier;
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

    // Toutes les instantiations de nos classes services
    private final NettoyeurDonnees nettoyeurDonnees;
    private final LecteurFichier lecteurFichier;
    private final GaresProvider garesProvider;
    private final CreateurDonnees createurDonnees;

    // Objet destiné à contenir tous les fichiers fournit par l'appel d'API du front
    private final FichierLu fichierLu = FichierLu.builder().build();

    public List<Gare> generationGOVI(DemandeGOVI demandeGOVI){
        // Création des gares qui doivent être remplis de nos retournements
        List<Gare> listGares = garesProvider.getGares();

        // Création de nos données conducteur via la lecture des fichiers pacific
        createurDonnees.creationConducteurs(fichierLu.getPacificj1());
        if(fichierLu.getPacificj2() != null){
            createurDonnees.creationConducteurs(fichierLu.getPacificj2());
        }

        // Création de nos retournements (et affectation quai/gare) via la lecture des fichiers BHL
        listGares = createurDonnees.creationRetournementBHL(fichierLu.getBhlj1(), demandeGOVI.getDate(), listGares, TypeFichierEnum.BHLJ1);
        if(fichierLu.getBhlj2() != null) {
            listGares = createurDonnees.creationRetournementBHL(fichierLu.getBhlj2(), demandeGOVI.getDate(), listGares, TypeFichierEnum.BHLJ2);
        }

        // Création de nos retournements (et affectation quai/gare) via la lecture du fichier RATP
        /*if(fichierLu.getRatp() != null) {
            listGares = createurDonnees.creationRetournementRATP(fichierLu.getRatp(),demandeGOVI.getDate(),listGares);
        }*/

        // Vider le cache des fichiers enregistrés précédemment fournit par le front /!\
        fichierLu.reset();

        return(listGares);
    }

    /**
     * Lit les fichier fournit par l'API et les convertis au format Workbook lisible par Java.
     * Elles sont ensuite stockés dans l'attribut 'fichierLu'
     * @param file est le fichier fournit par l'appel d'API du front
     * @param typeFichier est le type de fichier fournit à la lecture (BHL, Pacific, RATP, J ou J+1...)
     */
    public void readFile(MultipartFile file, TypeFichierEnum typeFichier) throws ExceptionLectureFichier {
        this.lecteurFichier.reader(file, typeFichier, fichierLu);
        log.info("File Read !");
        if(fichierLu.getBhlj1() != null
                && fichierLu.getBhlj2() != null
                && fichierLu.getRatp() != null
                && fichierLu.getPacificj1() != null
                && fichierLu.getPacificj2() != null){
            log.info("All files read !");
        }
    }
}
