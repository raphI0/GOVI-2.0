package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Data
public class Mission {
    private String codeMission;
    private String gareDepart;
    private String gareArrivee;
    private String heureArrivee;
    private String heureDepart;
    private TransporteurEnum transporteurEnum;
    private CouleurEnum couleurEnum;
    private Conducteur conducteurTrain;
    @Builder.Default
    private List<Conducteur> conducteursEVLoc = new ArrayList<>();
}
