package com.sncf.govi.service;

import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.exception.ExceptionLectureFichier;
import com.sncf.govi.service.model.fichierlecteur.FichierLu;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


@Service
public class LecteurFichier {
    public void reader(MultipartFile fichier, TypeFichierEnum typeFichier, FichierLu fichierLu) throws ExceptionLectureFichier {

        switch (typeFichier){
            case BHLJ1 -> fichierLu.setBhlj1(xlsReader(fichier));
            case BHLJ2 -> fichierLu.setBhlj2(xlsReader(fichier));
            case RATP -> fichierLu.setRatp(xlsReader(fichier));
            case PACIFICJ1 -> fichierLu.setPacificj1(csvToExcel(fichier));
            case PACIFICJ2 -> fichierLu.setPacificj2(csvToExcel(fichier));
            default -> throw new IllegalStateException("Type de fichier inattendu: " + typeFichier);
        }
    }

    public Workbook xlsReader(MultipartFile fichier) throws ExceptionLectureFichier {

        InputStream fileExcel;
        try {
            fileExcel = fichier.getInputStream();

        } catch (IOException e) {
            throw new ExceptionLectureFichier("La récupération du fichier a échouée",e);
        }
        Workbook excel;
        try {
            excel = WorkbookFactory.create(fileExcel);

        } catch (IOException e) {
            throw new ExceptionLectureFichier("Le passage du fichier au format excel a échouée",e);
        }

        return(excel);
    }
    public Workbook csvToExcel(MultipartFile fichier) throws ExceptionLectureFichier {
        try (Reader fichierCSV = new BufferedReader(new InputStreamReader(fichier.getInputStream()));
             CSVParser csv = new CSVParser(fichierCSV, CSVFormat.DEFAULT.builder().setDelimiter(';').build())) {

            Workbook workbook = new XSSFWorkbook(); // Crée un nouveau workbook

            Sheet sheet = workbook.createSheet("Sheet 1"); // Crée une nouvelle feuille

            int rowIndex = 0;
            for (CSVRecord csvRow : csv) {
                Row row = sheet.createRow(rowIndex++); // Crée une nouvelle ligne
                int columnIndex = 0;
                for (String csvCell : csvRow) {
                    Cell cell = row.createCell(columnIndex++); // Crée une nouvelle cellule
                    cell.setCellValue(csvCell); // Définit la valeur de la cellule
                }
            }

            return workbook;
        } catch (IOException e) {
            throw new ExceptionLectureFichier("Erreur lors de la conversion du CSV en Excel", e);
        }
    }
}
