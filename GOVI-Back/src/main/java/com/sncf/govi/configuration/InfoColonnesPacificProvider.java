package com.sncf.govi.configuration;

import lombok.Data;

/**
 * Récupère les bons numéros de colonnes dans le YML
 */
@Data
public class InfoColonnesPacificProvider {
    public int codeMission;
    public int typeMission;
    public int heureDebut;
    public int heureFin;
    public int codeADC;
}
