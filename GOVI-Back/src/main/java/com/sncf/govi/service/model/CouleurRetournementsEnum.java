package com.sncf.govi.service.model;

public enum CouleurRetournementsEnum {
    ROUGE("D52B1E"),
    CARBONE("3C3732");


    private String code;

    CouleurRetournementsEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}