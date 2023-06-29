package com.sncf.govi.configuration;

import lombok.Data;

/**
 * Récupère les bons numéros de colonnes dans le YML (pour le fichier RATP)
 */

@Data
public class InfoColonnesRATPProvider extends InfoColonnes{
    public int numVoie;
    public int codeMissionDepart;
    public int codeMissionArrivee;
    public int garesTrainArrivee;
    public int garesTrainDepart;
    public int heureArrivee;
    public int heureDepart;
}
