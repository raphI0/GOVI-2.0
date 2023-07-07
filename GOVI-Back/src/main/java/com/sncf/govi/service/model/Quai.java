package com.sncf.govi.service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Quai {
    private String id;
    private List<Retournement> retournements;

    public Quai(String id) {
        this.id = id;
        this.retournements = new ArrayList<>();
    }
}
