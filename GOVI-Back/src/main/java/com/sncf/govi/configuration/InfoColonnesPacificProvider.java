package com.sncf.govi.configuration;

import lombok.Data;

/**
 * Récupère les bons numéros de colonnes dans le YML
 */
@Data
public class InfoColonnesPacificProvider {
    private int codeMission;
    private int typeMission;
    private int heureDebut;
    private int heureFin;
    private int codeADC;
}
