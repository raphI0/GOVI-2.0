package com.sncf.govi.controller.model;

import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
public class DemandeGOVI {
    private HashMap<String,String> fichiers;
    private List<String> idGares;
    private Date date;
}
