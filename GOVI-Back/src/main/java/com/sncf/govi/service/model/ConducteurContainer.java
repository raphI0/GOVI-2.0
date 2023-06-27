package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class ConducteurContainer {
    private Conducteur conducteurTrain;
    @Builder.Default
    private List<Conducteur> conducteursEVLoc = new ArrayList<>();
}
