package com.sncf.govi.configuration;

import lombok.Data;

/**
 * Récupère les bons numéros de colonnes dans le YML
 */
@Data
public class InfoColonnesBHLProvider{
    private int numVoie;
    private int codeMissionDepart;
    private int codeMissionArrivee;
    private int garesTrainArrivee;
    private int garesTrainDepart;
    private int heureArrivee;
    private int heureDepart;

    private int nombreColonnes;
}
