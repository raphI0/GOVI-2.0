package com.sncf.govi.service.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Conducteur {
    private String residence;
    private String codeADC;
    private Boolean isPS;
    private Boolean isFS;
    @Builder.Default
    private String couleur = CouleurEnum.ROUGE.getCode();
}