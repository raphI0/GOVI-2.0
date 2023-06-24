package com.sncf.govi.service;

import com.sncf.govi.controller.model.TypeFichierEnum;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Service
public class CreateurDonnees {

    public void creationRetournement(Workbook tableau, String origineDesDonnees){
        if(origineDesDonnees.equals(TypeFichierEnum.BHL.name())){
            //Récup info colonne pour donnees BHL
        }

        if(origineDesDonnees.equals(TypeFichierEnum.RATP.name())){
            //Récup info colonnes pour données RATP
        }

    }
}
