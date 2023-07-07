package com.sncf.govi.configuration;

import lombok.Data;

/**
 * Récupère les bons numéros de colonnes dans le YML (pour le fichier RATP)
 */

@Data
public class InfoColonnesRATPProvider{
    private int codeMission;
    private int codeADC; //S07
    private int numeroRame; // T1,T2...
    private int manoeuvre; //R = Retournement, D= Degarage, G= Garage + Alias Gare + Voie arrivee et depart
    private int typeEvenement; //MSC, MEC
    private int numVoieDepart;
    private int numVoieArrivee;
}