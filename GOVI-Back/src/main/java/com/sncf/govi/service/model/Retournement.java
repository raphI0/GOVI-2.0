package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Retournement {
    private CouleurEnum couleur;
    @Builder.Default
    private List<Mission> missionsArrivee = new ArrayList<>();
    @Builder.Default
    private List<Mission> missionsDepart = new ArrayList<>();
}
