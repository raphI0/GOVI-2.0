package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Gare {
    private String nom;
    private String alias;
    private List<Quai> quais;
}
