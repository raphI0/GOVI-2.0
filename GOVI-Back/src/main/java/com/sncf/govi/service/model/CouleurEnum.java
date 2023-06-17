package com.sncf.govi.service.model;

public enum CouleurEnum {
    ROUGE("D52B1E"),
    ORANGE("E05206"),
    JAUNE_SAFRAN("FFB612"),
    VERT_POMME("82BE00"),
    VERT_ANIS("D2E100"),
    BLEU_PRIMAIRE("0088CE"),
    BLEU_CANARD("009AA6"),
    VIOLET("6E1E78"),
    PRUNE("A1006B"),
    FRAMBOISE("CD0037"),
    COOL_GRAY_1("E1E1E1"),
    COOL_GRAY_3("D7D7D7"),
    COOL_GRAY_5("B9B9B9"),
    COOL_GRAY_7("A0A0A0"),
    COOL_GRAY_9("747678"),
    COOL_GRAY_11("4D4F53"),
    WARM_GRAY_1("E0DED8"),
    WARM_GRAY_3("C3BEB4"),
    WARM_GRAY_5("AFA59B"),
    WARM_GRAY_7("988F86"),
    WARM_GRAY_9("827877"),
    WARM_GRAY_11("675C53"),
    CARBONE("3C3732"),
    BLANC("FFFFFF");

    private String code;

    CouleurEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
