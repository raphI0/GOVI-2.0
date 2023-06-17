package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Releve {
    private String gare;
    private String nextMission;
    private String currentMission;
}
