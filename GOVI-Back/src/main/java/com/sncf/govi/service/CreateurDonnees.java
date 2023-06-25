package com.sncf.govi.service;

import com.sncf.govi.configuration.InfoColonnesProvider;
import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateurDonnees {

    private final InfoColonnesProvider infoColonnesProvider;
    public List<Gare> creationRetournement(Workbook tableau, String origineDesDonnees, LocalDateTime dateFichier, List<Gare> gares) {
       if (origineDesDonnees.equals(TypeFichierEnum.BHL.name())) {
            //Récup info colonne pour donnees BHL
        }

        if (origineDesDonnees.equals(TypeFichierEnum.RATP.name())) {
            //Récup info colonnes pour données RATP
        }
        Sheet feuille = tableau.getSheetAt(0); // Accéder à la première feuille
        int rowCount = 0;
        int cellCount = 0;
        for (Row row : feuille) {
            rowCount++;
            if (rowCount % 1000 == 0) {
                log.info(String.valueOf(rowCount));
            }
            cellCount = 0;
            Retournement retournement = Retournement.builder().build();
            Mission missionDepart = Mission.builder().couleurEnum(CouleurEnum.BLEU_CANARD).build();
            Mission missionArrivee = Mission.builder().couleurEnum(CouleurEnum.BLEU_CANARD).build();
            String gare = "";
            String quai = "";
            for (Cell cell : row) {
                cellCount++;
                // Récupérez la valeur de la cellule
                String cellValue = getStringValue(cell);
                // Faites quelque chose avec la valeur de la cellule
                if (cellCount == infoColonnesProvider.numVoie) {
                    quai = cellValue;
                }
                else if (cellCount == infoColonnesProvider.codeMissionDepart) {
                    missionDepart.setCodeMission(cellValue);
                }
                else if (cellCount == infoColonnesProvider.codeMissionArrivee) {
                    missionArrivee.setCodeMission(cellValue);
                }
                else if (cellCount == infoColonnesProvider.garesTrainDepart) {
                    String[] garesSplitted = cellValue.split("/");
                    if(garesSplitted.length > 0) {
                        missionDepart.setGareArrivee(garesSplitted[0]);
                    }
                    if(garesSplitted.length == 2) {
                        missionDepart.setGareDepart(garesSplitted[1]);
                    }
                }
                else if (cellCount == infoColonnesProvider.garesTrainArrivee) {
                    String[] garesSplitted = cellValue.split("/");
                    if(garesSplitted.length > 0) {
                        missionArrivee.setGareArrivee(garesSplitted[0]);
                    }
                    if(garesSplitted.length == 2) {
                        missionArrivee.setGareDepart(garesSplitted[1]);
                    }
                }
                else if (cellCount == infoColonnesProvider.heureArrivee) {
                    try{
                        LocalTime date = LocalTime.parse(cellValue);
                        LocalDateTime newDate = dateFichier;
                        newDate = newDate.plusHours(date.getHour());
                        newDate = newDate.plusMinutes(date.getMinute());
                        newDate = newDate.plusSeconds(date.getSecond());

                        missionArrivee.setHeureArrivee(newDate.toString());
                    }catch (DateTimeParseException e){
                        log.error(e.getMessage());
                    }

                }
                else if (cellCount == infoColonnesProvider.heureDepart) {
                    try{
                        LocalTime date = LocalTime.parse(cellValue);
                        LocalDateTime newDate = dateFichier;
                        newDate = newDate.plusHours(date.getHour());
                        newDate = newDate.plusMinutes(date.getMinute());
                        newDate = newDate.plusSeconds(date.getSecond());

                        missionDepart.setHeureDepart(newDate.toString());
                    }catch (DateTimeParseException e){
                        log.error(e.getMessage());
                    }

                }
            }
            retournement.getMissionsDepart().add(missionDepart);
            retournement.getMissionsArrivee().add(missionArrivee);
            retournement.setCouleur(CouleurEnum.CARBONE);
            gares = affecterRetournement(retournement, gares, quai);
        }
        return gares;

    }
    private List<Gare> affecterRetournement(Retournement retournement, List<Gare> gares, String idQuai){
        for(Gare gare : gares){
            if(gare.getAlias().equals(retournement.getMissionsArrivee().get(0).getGareDepart())){
                for(Quai quai : gare.getQuais()){
                    if(quai.getId().equals(idQuai)){
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

    private String getStringValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> "";
        };
    }
}
