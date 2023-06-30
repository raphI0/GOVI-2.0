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
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateurDonnees {

    private final InfoColonnesBHLProvider infoColonnesBHLProvider;
    private final InfoColonnesPacificProvider infoColonnesPacificProvider;
    private final InfoColonnesRATPProvider infoColonnesRATPProvider;

    private HashMap<String, ConducteurContainer> conducteursParMission = new HashMap<>();


    /**
     * Crée des instances de retournement à partir d'un fichier Excel.
     *
     * @param tableau le fichier Excel avec lequel travailler
     * @param origineDesDonnees l'origine des données (Contenu dans TypeFichierEnum)
     * @param dateFichier la date à utiliser pour les instances de retournement
     * @param gares la liste des gares
     * @param estJ2 indique si le fichier est du Jour 2 (passe minuit)
     * @return la liste des gares avec des retournements créés
     */
    public List<Gare> creationRetournement(Workbook tableau, String origineDesDonnees, LocalDateTime dateFichier, List<Gare> gares, boolean estJ2) {

        Sheet feuille = tableau.getSheetAt(0); // Accéder à la première feuille

        int cellCount = 0;

        for (Row row : feuille) {

            cellCount = 0;
            // Crée des retournements et missions vides
            Retournement retournement = Retournement.builder().build();
            Mission missionDepart = Mission.builder().couleurEnum(CouleurEnum.BLEU_CANARD).build();
            Mission missionArrivee = Mission.builder().couleurEnum(CouleurEnum.BLEU_CANARD).build();
            String gare = "";
            String quai = "";
            boolean retournementInvalide = false;

            for (Cell cell : row) {

                cellCount++;
                // Récupére la valeur de la cellule
                String cellValue = getStringValue(cell);
                // Récupère le quai
                if (cellCount == infoColonnesBHLProvider.numVoie) {
                    quai = cellValue;
                }
                // Récupère le code mission de départ
                else if (cellCount == infoColonnesBHLProvider.codeMissionDepart) {
                    missionDepart.setCodeMission(cellValue);
                }
                // Récupère le code mission d'arrivée
                else if (cellCount == infoColonnesBHLProvider.codeMissionArrivee) {
                    missionArrivee.setCodeMission(cellValue);
                }
                // Gares train départ sous forme "CLX/RYR" donc on split la string en deux avec le séparateur "/"
                else if (cellCount == infoColonnesBHLProvider.garesTrainDepart) {
                    String[] garesSplitted = cellValue.split("/");
                    // On vérifie d'abord si le résultat du string est non nul
                    if(garesSplitted.length > 0) {
                        missionDepart.setGareArrivee(garesSplitted[0]);
                    }
                    if(garesSplitted.length == 2) {
                        missionDepart.setGareDepart(garesSplitted[1]);
                    }
                }
                // Pareil pour les gares de la mission d'arrivee
                else if (cellCount == infoColonnesBHLProvider.garesTrainArrivee) {
                    String[] garesSplitted = cellValue.split("/");
                    if(garesSplitted.length > 0) {
                        missionArrivee.setGareArrivee(garesSplitted[0]);
                    }
                    if(garesSplitted.length == 2) {
                        missionArrivee.setGareDepart(garesSplitted[1]);
                    }
                }
                /* Heure d'arrivee : on ajoute à la date indiquée par l'utilisateur
                   l'heure de la mission du excel
                   ainsi 01/01/99 0h00 devient 01/01/99 23h00
                */
                else if (cellCount == infoColonnesBHLProvider.heureArrivee) {
                    try{
                        LocalTime date = LocalTime.parse(cellValue);
                        LocalDateTime newDate = dateFichier;
                        newDate = newDate.plusHours(date.getHour());
                        newDate = newDate.plusMinutes(date.getMinute());
                        newDate = newDate.plusSeconds(date.getSecond());

                        if(estJ2){
                            newDate = newDate.plusDays(1);
                            if((date.getHour() >= 1 && date.getMinute() > 30) || date.getHour() >= 2){
                                retournementInvalide = true;
                            }
                        }

                        missionArrivee.setHeureArrivee(newDate.toString());
                    }catch (DateTimeParseException e){
                        log.error(e.getMessage());
                    }

                }
                // Pareil pour l'heure de départ
                else if (cellCount == infoColonnesBHLProvider.heureDepart) {
                    try{
                        LocalTime date = LocalTime.parse(cellValue);
                        LocalDateTime newDate = dateFichier;
                        newDate = newDate.plusHours(date.getHour());
                        newDate = newDate.plusMinutes(date.getMinute());
                        newDate = newDate.plusSeconds(date.getSecond());

                        if(estJ2){
                            newDate = newDate.plusDays(1);
                            if((date.getHour() >= 1 && date.getMinute() > 30) || date.getHour() >= 2){
                                retournementInvalide = true;
                            }
                        }

                        missionDepart.setHeureDepart(newDate.toString());
                    }catch (DateTimeParseException e){
                        log.error(e.getMessage());
                    }

                }
            }
            // On ajoute les missions remplies dans le retournement
            if(!retournementInvalide) {
                missionArrivee = affecterConducteurAMission(missionArrivee);
                missionDepart = affecterConducteurAMission(missionDepart);
                retournement.getMissionsDepart().add(missionDepart);
                retournement.getMissionsArrivee().add(missionArrivee);
                retournement.setCouleur(CouleurEnum.CARBONE);
                gares = affecterRetournement(retournement, gares, quai);
            }
        }
        return gares;

    }

    /**
     * Attribue un conducteur à une mission.
     *
     * @param mission la mission à laquelle attribuer un conducteur
     * @return la mission avec un conducteur attribué
     */
    private Mission affecterConducteurAMission(Mission mission){
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
            // Si l'alias de la gare correspond
            if(gare.getAlias().equals(retournement.getMissionsArrivee().get(0).getGareDepart())){
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

    /**
     * Récupère la valeur d'une cellule
     * @param cell
     * @return
     */
    private String getStringValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> "";
        };
    }

    public void creationConducteurs(Workbook tableau){
        Sheet feuille = tableau.getSheetAt(0); // Accéder à la première feuille
        for (Row row : feuille) {
            String codeMission = "";
            String typeMission = "";
            Conducteur conducteur = Conducteur.builder().build();
            int cellCount = 0;
            for (Cell cell : row) {
                cellCount++;
                // Récupére la valeur de la cellule
                String cellValue = getStringValue(cell);
                // Récupère le code mission
                if (cellCount == infoColonnesPacificProvider.codeMission) {
                    codeMission = cellValue;
                }
                else if (cellCount == infoColonnesPacificProvider.codeADC) {
                    conducteur.setCodeADC(cellValue);
                }
                else if (cellCount == infoColonnesPacificProvider.typeMission) {
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
}