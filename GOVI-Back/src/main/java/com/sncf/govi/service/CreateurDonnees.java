package com.sncf.govi.service;

import com.sncf.govi.configuration.InfoColonnesPacificProvider;
import com.sncf.govi.configuration.InfoColonnesBHLProvider;
import com.sncf.govi.configuration.InfoColonnesRATPProvider;
import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateurDonnees {

    private final InfoColonnesBHLProvider infoColonnesBHLProvider;
    private final InfoColonnesPacificProvider infoColonnesPacificProvider;
    private final InfoColonnesRATPProvider infoColonnesRATPProvider;

    private final AffecteurDonnees affecteurDonnees;

    private final HashMap<String, ConducteurContainer> conducteursParMission = new HashMap<>();

    public List<Gare> creationRetournementRATP(Workbook tableau, LocalDateTime dateFichier, List<Gare> gares){

        // Accéder à la première feuille
        Sheet feuille = tableau.getSheetAt(0);

        int cellCount;
        boolean firstRow = true;

        for (Row row : feuille) {

            // permet de sauter la première ligne avec le nom des colonnes
            if (firstRow) {
                firstRow = false;
                continue;
            }
            cellCount = 0;
            // Crée des retournements et missions vides
            Retournement retournement = Retournement.builder().build();
            Mission missionDepart = Mission.builder().couleurEnum(CouleurEnum.BLEU_CANARD).build();
            Mission missionArrivee = Mission.builder().couleurEnum(CouleurEnum.BLEU_CANARD).build();
            String quai = "";

            for (Cell cell : row) {

                cellCount++;
                // Récupéré la valeur de la cellule
                String cellValue = getStringValue(cell);

                // Récupère le quai
                if (cellCount == infoColonnesBHLProvider.getNumVoie()) {
                    quai = cellValue;
                }

                // Récupère le code mission de départ
                else if (cellCount == infoColonnesBHLProvider.getCodeMissionDepart()) {
                    missionDepart.setCodeMission(cellValue);
                }

                // Récupère le code mission d'arrivée
                else if (cellCount == infoColonnesBHLProvider.getCodeMissionArrivee()) {
                    missionArrivee.setCodeMission(cellValue);
                }

                // Gares train départ sous forme "CLX/RYR" donc on sépare la chaine de caractère en deux avec le séparateur "/"
                else if (cellCount == infoColonnesBHLProvider.getGaresTrainDepart()) {
                    assignationGares(missionDepart, cellValue);
                }

                // Pareil pour les gares de la mission d'arrivée
                else if (cellCount == infoColonnesBHLProvider.getGaresTrainArrivee()) {
                    assignationGares(missionArrivee, cellValue);
                }

                /* Heure d'arrivée : on ajoute à la date indiquée par l'utilisateur
                   l'heure de la mission de l'Excel
                   ainsi 01/01/99 0h00 devient 01/01/99 23h00
                */
                else if (cellCount == infoColonnesBHLProvider.getHeureArrivee()) {
                    //Création d'un objet list d'un seul élément primitif, pour pourvoir le passer par référence (et donc que la méthode modifie tout sans return)
                    missionArrivee.setHeureArrivee(calculeDateEtHeure(dateFichier, false, cellValue));
                }
                // Pareil pour l'heure de départ
                else if (cellCount == infoColonnesBHLProvider.getHeureDepart()) {
                    // Idem
                    missionDepart.setHeureDepart(calculeDateEtHeure(dateFichier, false, cellValue));
                }

            }
            // On ajoute les missions remplies dans le retournement
            missionArrivee = affecteurDonnees.affecterConducteurAMission(missionArrivee,conducteursParMission);
            missionDepart = affecteurDonnees.affecterConducteurAMission(missionDepart,conducteursParMission);
            gares = affecteurDonnees.affectation(missionArrivee, missionDepart, retournement, gares, quai);

        }
        return gares;

    }

    /**
     * Crée des instances de retournement à partir d'un fichier BHL (SNCF).
     *
     * @param tableau le fichier Excel BHL avec lequel travailler
     * @param dateFichier la date à utiliser pour les instances de retournement (saisi par l'utilisateur dans le front)
     * @param gares la liste des gares terminus (ou non) affichable sur le GOVI de la ligne B
     * @param typeFichier permet d'indiquer ensuite si le fichier est du Jour 2 (passe minuit et tout ce que cela implique)
     * @return la liste des gares avec des retournements créés et insérés
     */
    public List<Gare> creationRetournementBHL(Workbook tableau, LocalDateTime dateFichier, List<Gare> gares, TypeFichierEnum typeFichier) {

        // Boolean qui permet de savoir si notre fichier est celui du J2 ou non
        boolean estJ2 = typeFichier.equals(TypeFichierEnum.BHLJ2);

        Sheet feuille = tableau.getSheetAt(0); // Accéder à la première feuille

        int cellCount;
        boolean firstRow = true;

        for (Row row : feuille) {

            // permet de sauter la première ligne avec le nom des colonnes
            if (firstRow) {
                firstRow = false;
                continue;
            }
            cellCount = 0;
            // Crée des retournements et missions vides
            Retournement retournement = Retournement.builder().build();
            Mission missionDepart = Mission.builder().couleurEnum(CouleurEnum.BLEU_CANARD).build();
            Mission missionArrivee = Mission.builder().couleurEnum(CouleurEnum.BLEU_CANARD).build();
            String quai = "";

            for (Cell cell : row) {

                cellCount++;
                // Récupéré la valeur de la cellule
                String cellValue = getStringValue(cell);

                // Récupère le quai
                if (cellCount == infoColonnesBHLProvider.getNumVoie()) {
                    quai = cellValue;
                }

                // Récupère le code mission de départ
                else if (cellCount == infoColonnesBHLProvider.getCodeMissionDepart()) {
                    missionDepart.setCodeMission(cellValue);
                }

                // Récupère le code mission d'arrivée
                else if (cellCount == infoColonnesBHLProvider.getCodeMissionArrivee()) {
                    missionArrivee.setCodeMission(cellValue);
                }

                // Gares train départ sous forme "CLX/RYR" donc on sépare la chaine de caractère en deux avec le séparateur "/"
                else if (cellCount == infoColonnesBHLProvider.getGaresTrainDepart()) {
                    assignationGares(missionDepart, cellValue);
                }

                // Pareil pour les gares de la mission d'arrivée
                else if (cellCount == infoColonnesBHLProvider.getGaresTrainArrivee()) {
                    assignationGares(missionArrivee, cellValue);
                }

                /* Heure d'arrivée : on ajoute à la date indiquée par l'utilisateur
                   l'heure de la mission de l'Excel
                   ainsi 01/01/99 0h00 devient 01/01/99 23h00
                */
                else if (cellCount == infoColonnesBHLProvider.getHeureArrivee()) {
                    //Création d'un objet list d'un seul élément primitif, pour pourvoir le passer par référence (et donc que la méthode modifie tout sans return)
                    missionArrivee.setHeureArrivee(calculeDateEtHeure(dateFichier, estJ2, cellValue));
                }
                // Pareil pour l'heure de départ
                else if (cellCount == infoColonnesBHLProvider.getHeureDepart()) {
                    // Idem
                    missionDepart.setHeureDepart(calculeDateEtHeure(dateFichier, estJ2, cellValue));
                }

            }
            // On ajoute les missions remplies dans le retournement
            missionArrivee = affecteurDonnees.affecterConducteurAMission(missionArrivee,conducteursParMission);
            missionDepart = affecteurDonnees.affecterConducteurAMission(missionDepart,conducteursParMission);
            gares = affecteurDonnees.affectation(missionArrivee, missionDepart, retournement, gares, quai);

        }
        return gares;

    }

    /**
     * Méthode qui crée des instances de conducteurs à partir du fichier pacific fournit par l'utilisateur.
     * Ceux-ci sont ensuite stockés dans la hashmap 'conducteursParMission'
     * @param tableau fichier pacific fournit par l'utilisateur
     */
    public void creationConducteurs(Workbook tableau){
        Sheet feuille = tableau.getSheetAt(0); // Accéder à la première feuille
        for (Row row : feuille) {
            String codeMission = "";
            String typeMission = "";
            Conducteur conducteur = Conducteur.builder().build();
            int cellCount = 0;
            for (Cell cell : row) {
                cellCount++;
                // Récupéré la valeur de la cellule
                String cellValue = getStringValue(cell);
                // Récupère le code mission
                if (cellCount == infoColonnesPacificProvider.getCodeMission()) {
                    codeMission = cellValue;
                }
                else if (cellCount == infoColonnesPacificProvider.getCodeADC()) {
                    conducteur.setCodeADC(cellValue);
                }
                else if (cellCount == infoColonnesPacificProvider.getTypeMission()) {
                    typeMission = cellValue;
                }
            }

            ConducteurContainer conducteurContainer = conducteursParMission.get(codeMission);
            if(conducteurContainer == null){
                conducteurContainer = ConducteurContainer.builder().build();
                conducteursParMission.put(codeMission, conducteurContainer);
            }
            if("Train".equals(typeMission)){
                conducteurContainer.setConducteurTrain(conducteur);
                conducteursParMission.put(codeMission, conducteurContainer);
            }
            else if("EV Loc".equals(typeMission)){
                conducteurContainer.getConducteursEVLoc().add(conducteur);
                conducteursParMission.put(codeMission, conducteurContainer);
            }
        }
    }

    /**
     * Cette méthode permet d'attribuer sa gare de départ ET d'arrivée à une mission via la colonne BHL qui le renseigne
     * @param mission est la mission dont nous cherchons les gares d'arrivée/depart
     * @param cellValue est la cellule qui contient les deux gares séparées par un '/'
     */
    private void assignationGares(Mission mission, String cellValue) {
        String[] garesSeparees = cellValue.split("/");
        // On vérifie d'abord si le résultat du string est non nul
        if(garesSeparees.length > 0) {
            mission.setGareDepart(garesSeparees[0]);
        }
        if(garesSeparees.length == 2) {
            mission.setGareArrivee(garesSeparees[1]);
        }
    }

    /**
     * Cette méthode modifie la date de chaque retournement, par rapport à la date entrée par l'utilisateur.
     * Ainsi notre retournement récupère son heure dans le BHL et sa date via l'utilisateur
     *
     * @param dateFichier est la date entrée par l'utilisateur dans l'IHM du front-end
     * @param estJ2 est un bool qui permet de savoir s'il s'agit du fichier J+1 ou non
     * @param cellValue valeur de la cellule contenant la date/heure de notre retournement
     * @return l'heure du retournement
     */
    private String calculeDateEtHeure(LocalDateTime dateFichier, boolean estJ2, String cellValue) {

        LocalDateTime newDate = dateFichier;
        try{
            // On découpe notre String temps, et on la réduit de 24H si elle les dépasse
            String[] timeParts = cellValue.split(":");
            int hours = Integer.parseInt(timeParts[0]) %24;
            int minutes = Integer.parseInt(timeParts[1]);
            int seconds = Integer.parseInt(timeParts[2]);

            // On modifie l'heure renseigné par l'utilisateur via la découpe temporelle précédente
            newDate = newDate.plusHours(hours);
            newDate = newDate.plusMinutes(minutes);
            newDate = newDate.plusSeconds(seconds);

            // D'office, si le retournement est à J+1, mais dépasse la plage horaire de notre fin de journée sur la B
            // (1H30 du matin), il est défini comme invalide et ne sera pas pris en compte
            if(estJ2){
                // Si J2, on définit notre retournement comme se passant le lendemain
                newDate = newDate.plusDays(1);
            }

        }catch (DateTimeParseException e){
            log.error(e.getMessage());
        }

        // Et on finit par renvoyer la date et l'heure du retournement
        return (newDate.toString());
    }

    /**
     * Récupère la valeur d'une cellule, pour la retourner au format chaine de caractère (String)
     *
     * @param cell est la cellule dont il faut retourner le contenu =
     * @return la valeur de la cellule au format String
     */
    private String getStringValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> "";
        };
    }
}