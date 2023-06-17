package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Mission {
    private String codeMission;
    private String gareDepart;
    private String gareArrivee;
    private Date heureArrivee;
    private Date heureDepart;
    private TransporteurEnum transporteurEnum;
    private CouleurEnum couleurEnum;
}
