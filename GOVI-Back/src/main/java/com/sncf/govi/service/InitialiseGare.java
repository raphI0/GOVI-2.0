package com.sncf.govi.service;

import com.sncf.govi.configuration.GaresConfig;
import com.sncf.govi.service.model.Gare;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitialiseGare {
    private List<Gare> listGares = new ArrayList<>();
    private final GaresConfig garesConfig;
    public List<Gare> creationGares(){
        return (garesConfig.getGares());
    }
}