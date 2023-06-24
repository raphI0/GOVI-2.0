package com.sncf.govi.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

import java.io.IOException;
import java.util.Iterator;

@Service
public class NettoyeurDonnees {

    public static boolean isAlpha(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public Workbook nettoyageDonneesConducteurs(Workbook donneesConducteurs){

    //----------------------------------------------------------------------------------------------------------
    // Etape 1 : suppression des trains avec mauvaise mission (ligne H,K...) ou nature (TAXI...)
    //----------------------------------------------------------------------------------------------------------
    Sheet sheet = donneesConducteurs.getSheetAt(0); // Accéder à la première feuille


        // Parcourir les lignes de la dernière à la première
        for (int i = sheet.getLastRowNum(); i >= 0; i--) {
            Row row = sheet.getRow(i);

            if (row != null) {
                Cell cell = row.getCell(0); // Accéder à la première cellule
                if (cell == null || cell.toString().length() != 6 || !isNumeric(cell.toString().substring(4)) || !isAlpha(cell.toString().substring(0, 4))) { // Tester le critère
                    // Si le critère ne correspond pas, supprimer la ligne
                    if (i != sheet.getLastRowNum()) {
                        sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                    }
                    sheet.removeRow(row);
                }
            }
        }


    //#----------------------------------------------------------------------------------------------------------
    //# Etape 2 : Creation des ADC avec le format adequat
    //#----------------------------------------------------------------------------------------------------------

    //#----------------------------------------------------------------------------------------------------------
    //# Etape 4 : Creation de la colonne PSFS (Prise de service, Fin de Service)
    //#----------------------------------------------------------------------------------------------------------

    //#----------------------------------------------------------------------------------------------------------
    //# Etape 5 : Creation et renseignement de la colonne relève GDS
    //#----------------------------------------------------------------------------------------------------------

        return (DonneesConducteurs);
    }

    public Workbook fusionJ1J2(Workbook donneesJ1, Workbook donneesJ2) {
        // Créer un nouveau workbook pour les données fusionnées
        Workbook donneesFusionnées = new XSSFWorkbook();

        // Créer une nouvelle feuille de calcul dans le workbook fusionné
        Sheet sheetFusion = donneesFusionnées.createSheet("Données Fusionnées");

        // Parcourir les données dans le premier workbook et les copier dans le workbook fusionné
        copierDonneesWorkbook(donneesJ1, sheetFusion);

        // Parcourir les données dans le deuxième workbook et les copier dans le workbook fusionné
        copierDonneesWorkbook(donneesJ2, sheetFusion);

        return (donneesFusionnées);
    }

    private void copierDonneesWorkbook(Workbook workbook, Sheet sheetFusion) {
        Sheet sheetSource = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheetSource.iterator();
        int rowCount = 0;
        if(sheetFusion.getLastRowNum() > 0) {
            rowCount = sheetFusion.getLastRowNum() + 1;
        }

        while (rowIterator.hasNext()) {
            Row rowSource = rowIterator.next();
            Row rowFusion = sheetFusion.createRow(rowCount++);

            Iterator<Cell> cellIterator = rowSource.cellIterator();
            int cellCount = 0;
            while (cellIterator.hasNext()) {
                Cell cellSource = cellIterator.next();
                Cell cellFusion = rowFusion.createCell(cellCount++);
                switch (cellSource.getCellType()) {
                    case STRING:
                        cellFusion.setCellValue(cellSource.getStringCellValue());
                        break;
                    case NUMERIC:
                        cellFusion.setCellValue(cellSource.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        cellFusion.setCellValue(cellSource.getBooleanCellValue());
                        break;
                    case FORMULA:
                        cellFusion.setCellFormula(cellSource.getCellFormula());
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /*public void nettoyageConducteurs{

    }*/
}
