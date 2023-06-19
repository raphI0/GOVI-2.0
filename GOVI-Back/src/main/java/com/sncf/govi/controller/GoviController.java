package com.sncf.govi.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GoviController {
    @PostMapping(value="/generationGOVI", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public List<String> appelGenerationGOVI(@RequestBody String[] fichiers){
        List<String> a = new ArrayList<>();
        a.add("GOVI.ppt");
        return a;
    }
}
