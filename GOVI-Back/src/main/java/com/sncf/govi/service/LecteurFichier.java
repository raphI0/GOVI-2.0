package com.sncf.govi.service;

import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.model.fichierlecteur.FichierLu;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class LecteurFichier {
    public void reader(MultipartFile fichier, TypeFichierEnum typeFichier, FichierLu fichierLu) {

        switch (typeFichier){
            case BHLJ1, RATP, BHLJ2 -> fichierLu.setBhlj1(xlsreader(fichier));
            case PACIFICJ1, PACIFICJ2 -> fichierLu.setPacific1(csvToExcel(fichier));
        }
    }

    public Workbook xlsreader(MultipartFile fichier) {

        InputStream fileexcel = null;
        try {
            fileexcel = fichier.getInputStream();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Workbook excel;
        try {
            excel = WorkbookFactory.create(fileexcel);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return(excel);
    }
    public Workbook csvToExcel(MultipartFile fichier) {
        try (Reader filecsv = new BufferedReader(new InputStreamReader(fichier.getInputStream()));
             CSVParser csv = new CSVParser(filecsv, CSVFormat.DEFAULT.builder().setDelimiter(';').build())) {

            Workbook workbook = new XSSFWorkbook(); // Crée un nouveau workbook

            Sheet sheet = workbook.createSheet("Sheet 1"); // Crée une nouvelle feuille

            int rowIndex = 0;
            for (CSVRecord record : csv) {
                Row row = sheet.createRow(rowIndex++); // Crée une nouvelle ligne
                int columnIndex = 0;
                for (String value : record) {
                    Cell cell = row.createCell(columnIndex++); // Crée une nouvelle cellule
                    cell.setCellValue(value); // Définit la valeur de la cellule
                }
            }

            return workbook;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la conversion du CSV en Excel", e);
        }
    }
}
