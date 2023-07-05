package com.sncf.govi.service.model.fichierlecteur;

import lombok.Builder;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;

@Builder
@Data
public class FichierLu {

    Workbook bhlj1;
    Workbook bhlj2;

    Workbook ratp;

    Workbook pacificj1;
    Workbook pacificj2;

    // Stockage après fusion J1 et J2
    Workbook bhl;
    Workbook pacific;

    /**
     * Réinitialise tous ses attributs (issu de l'API) à la fin de l'exécution.
     * Évite d'avoir des conflits de fichiers erronés si deux govi sont générés d'affiler
     */
    public void reset() {
        this.pacificj1 = null;
        this.pacificj2 = null;
        this.bhlj1 = null;
        this.bhlj2 = null;
        this.ratp = null;
    }
}
