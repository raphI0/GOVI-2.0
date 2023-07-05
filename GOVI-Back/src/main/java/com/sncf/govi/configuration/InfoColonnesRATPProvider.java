package com.sncf.govi.configuration;

import lombok.Data;

/**
 * Récupère les bons numéros de colonnes dans le YML (pour le fichier RATP)
 */

@Data
public class InfoColonnesRATPProvider{
    public int codeMission;
    public int codeADC; //S07
    public int numeroRame; // T1,T2...
    public int manoeuvre; //R = Retournement, D= Degarage, G= Garage + Alias Gare + Voie arrivee et depart
    public int typeEvenement; //MSC, MEC
    public int numVoieDepart;
    public int numVoieArrivee;
}