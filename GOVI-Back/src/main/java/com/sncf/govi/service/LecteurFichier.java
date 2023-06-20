package com.sncf.govi.service;

import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.model.fichierlecteur.FichierLu;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;


@Service
public class LecteurFichier {
    public FichierLu reader(HashMap<String, String> fichiers) {

        FichierLu fichierLu = FichierLu.builder().build();

        for (HashMap.Entry<String, String> fichier : fichiers.entrySet()) {

            String fichierXLS = "C:\\Users\\darck\\Desktop\\GOVI\\Data pour analyse\\Fonctionnelles\\Données sources\\07-01-23\\données_gov_07012023.xls";

            if(fichier.getKey().equals(TypeFichierEnum.BHLJ1.name())){

                fichierLu.setBhlj1(xlsreader(fichierXLS));
            }
            else if(fichier.getKey().equals(TypeFichierEnum.BHLJ2.name())){

                fichierLu.setBhlj2(xlsreader(fichierXLS));
            }
            else if(fichier.getKey().equals(TypeFichierEnum.RATP.name())){

                fichierLu.setRatp(xlsreader(fichierXLS));
            }
            else if(fichier.getKey().equals(TypeFichierEnum.PACIFICJ1.name())){

                fichierLu.setPacific1(csvreader(fichier.getValue()));
            }
            else if(fichier.getKey().equals(TypeFichierEnum.PACIFICJ2.name())){

                fichierLu.setPacific2(csvreader(fichier.getValue()));
            }

            }
        return(fichierLu);
        }

    public Workbook xlsreader(String fichier) {

        FileInputStream fileexcel = null;
        try {
            fileexcel = new FileInputStream(new File(fichier));

        } catch (FileNotFoundException e) {
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

    public CSVParser csvreader(String fichier){

        Reader filecsv = null;
        try {
            filecsv = new FileReader(fichier);
        } catch (FileNotFoundException e) {
            
        }
        CSVParser csv = null;
        try {
            csv = new CSVParser(filecsv, CSVFormat.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return (csv);

    }
}
