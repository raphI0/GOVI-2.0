package com.sncf.govi.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class DemandeGOVI {
    private HashMap<String, String> fichiers;
    private List<String> idGares;
    private Date date;
}
