package com.sncf.govi.service.model.fichierlecteur;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.csv.CSVParser;
import org.apache.poi.ss.usermodel.Workbook;

@Builder
@Data
public class FichierLu {
    Workbook bhlj1;
    Workbook bhlj2;
    Workbook ratp;
    Workbook pacific1;
    Workbook pacific2;
}
