package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Retournement {
    private CouleurEnum couleur;
}
