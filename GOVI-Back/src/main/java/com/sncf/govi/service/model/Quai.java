package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Quai {
    private String id;
    @Builder.Default
    private List<Retournement> retournements = new ArrayList<>();
}
