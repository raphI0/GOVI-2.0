package com.sncf.govi.configuration;

import com.sncf.govi.service.model.Gare;
import lombok.Data;

import java.util.List;

/**
 * Provides all the barcodes for special actions, maintenance etc. from the YML file
 */
@Data
public class GaresProvider {
    List<Gare> gares;
}
