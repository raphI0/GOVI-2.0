package com.sncf.govi.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

import java.io.IOException;
import java.util.Iterator;

@Service
public class NettoyeurDonnees {

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
        int rowCount = sheetFusion.getLastRowNum();

        while (rowIterator.hasNext()) {
            Row rowSource = rowIterator.next();
            Row rowFusion = sheetFusion.createRow(rowCount++);

            Iterator<Cell> cellIterator = rowSource.cellIterator();
            int cellCount = 0;
            while (cellIterator.hasNext()) {
                Cell cellSource = cellIterator.next();
                Cell cellFusion = rowFusion.createCell(cellCount++);
                cellFusion.setCellValue(cellSource.getStringCellValue());
            }
        }
    }

    /*public void nettoyageConducteurs{

    }*/
}
