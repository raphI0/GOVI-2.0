package com.sncf.govi.controller;

import com.sncf.govi.controller.model.DemandeGOVI;
import com.sncf.govi.service.Orchestrateur;
import com.sncf.govi.service.model.Gare;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class GoviController {
    private final Orchestrateur orchestrateur;
    @PostMapping(value="/generationGOVI", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<String> appelGenerationGOVI(@RequestBody List<String> demandeGOVI){
        List<String> a = new ArrayList<>();
        a.add("GOVI.ppt");

        List<Gare> listGareAvecRetournement = orchestrateur.generationGOVI(new DemandeGOVI());
        return a;
    }
}