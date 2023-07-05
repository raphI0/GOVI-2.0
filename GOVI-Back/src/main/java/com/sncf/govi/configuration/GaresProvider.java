package com.sncf.govi.configuration;

import com.sncf.govi.service.model.Gare;
import lombok.Data;

import java.util.List;

/**
 * Récupère les gares dans le YML
 */
@Data
public class GaresProvider {
    List<Gare> gares;
}
