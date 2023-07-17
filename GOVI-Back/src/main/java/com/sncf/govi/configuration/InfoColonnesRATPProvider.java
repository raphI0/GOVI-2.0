package com.sncf.govi.configuration;

import lombok.Data;

import java.util.List;

/**
 * Récupère les bons numéros de colonnes dans le YML (pour le fichier RATP)
 */

@Data
public class InfoColonnesRATPProvider{
    // numéros de colonnes
    private int codeMission; // SOSI40, KFAR02...
    private int codeADC; //S07 ...
    private int numeroRame; // T1,T2...
    private int manoeuvre; //R = Retournement, D= Degarage, G= Garage + Alias Gare + Voie arrivee et depart
    private int typeEvenement; //MSC, MEC...
    private int numVoieDepart; //Alias gare + voie OU  juste voie
    private int numVoieArrivee; //Alias gare + voie OU juste voie
    private int gareArrivee; //Alias gare
    private int gareDepart; //Alias gare
    private int heureArrivee; // Heure au format HH:MM:SS
    private int heureDepart; // Heure au format HH:MM:SS

    private String manoeuvreVide; //Ce qu'est une manœuvre vide dans le fichier RATP, en général '-'
    private String manoeuvreGarage; //Ce qu'est une manœuvre Garage dans le fichier RATP, en général "G"
    private String manoeuvreDegarage; //Ce qu'est une manœuvre Degarage dans le fichier RATP, en général "D"
    private String manoeuvreRetournement; //Ce qu'est une manœuvre Retournement dans le fichier RATP, en général "R"
    private List<String> typeEvenementDepart; // Ce qu'est un évènement de départ dans le fichier RATP, en général "MSC"
    private List<String> typeEvenementArrivee; // Ce qu'est un évènement d'arrivée dans le fichier RATP, en général "MEC"

}