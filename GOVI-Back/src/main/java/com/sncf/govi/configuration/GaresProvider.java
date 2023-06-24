package com.sncf.govi.configuration;

import com.sncf.govi.service.model.Gare;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class GaresProvider {
    List<Gare> gares;
}
