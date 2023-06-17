package com.sncf.govi.controller;

import com.sncf.govi.service.model.Gare;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GoviController {
@PostMapping("/generationGOVI")
    public List<Gare> appelGenerationGOVI(@RequestBody List<String> fichiers, @RequestBody List<String> gares){

        return (new ArrayList<>());
    }
}
