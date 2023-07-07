package com.sncf.govi.controller;

import com.sncf.govi.controller.model.DemandeGOVI;
import com.sncf.govi.controller.model.TypeFichierEnum;
import com.sncf.govi.service.Orchestrateur;
import com.sncf.govi.service.exception.ExceptionLectureFichier;
import com.sncf.govi.service.model.Gare;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class GoviController {
    private final Orchestrateur orchestrateur;
    @PostMapping(value="/generationGOVI", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<Gare> appelGenerationGOVI(@RequestBody DemandeGOVI demandeGOVI){
        return orchestrateur.generationGOVI(demandeGOVI);
    }

    @PostMapping(value="/generationGOVI/file", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public void ajoutFichier(@RequestParam("file") MultipartFile file,
                             @RequestParam("typeFichier") TypeFichierEnum typeFichier) throws ExceptionLectureFichier {
        orchestrateur.readFile(file, typeFichier);
    }
}