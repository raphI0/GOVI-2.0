package com.sncf.govi.configuration;

import lombok.Data;

/**
 * Récupère les bons numéros de colonnes dans le YML
 */
@Data
public class InfoColonnesBHLProvider{
    public int numVoie;
    public int codeMissionDepart;
    public int codeMissionArrivee;
    public int garesTrainArrivee;
    public int garesTrainDepart;
    public int heureArrivee;
    public int heureDepart;
}
