package com.sncf.govi.configuration;

import lombok.Data;

/**
 *
 */
@Data
public class InfoColonnesProvider {
    public int numVoie;
    public int codeMissionDepart;
    public int codeMissionArrivee;
    public int garesTrainArrivee;
    public int garesTrainDepart;
    public int heureArrivee;
    public int heureDepart;
}
