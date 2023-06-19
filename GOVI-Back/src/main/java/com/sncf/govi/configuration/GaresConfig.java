package com.sncf.govi.configuration;

import com.sncf.govi.service.model.Gare;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "gares")
public class GaresConfig {
    @Getter
    private List<Gare> gares = new ArrayList<>();

}
