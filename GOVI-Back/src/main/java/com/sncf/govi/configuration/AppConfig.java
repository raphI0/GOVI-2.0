package com.sncf.govi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Global App Configuration
 */
@Configuration
@Slf4j
public class AppConfig {

    /**
     * Provider for the gares
     *
     * @return the provider
     */
    @Bean
    @ConfigurationProperties(prefix = "gares")
    public GaresProvider garesConfigProvider() {
        return new GaresProvider();
    }

    /**
     * Provider for the num colonnes
     *
     * @return the provider
     */
    @Bean
    @ConfigurationProperties(prefix = "numcolonnes.bhl")
    public InfoColonnesBHLProvider infoColonnesBHLProvider() {
        return new InfoColonnesBHLProvider();
    }

    /**
     * Provider for the num colonnes
     *
     * @return the provider
     */
    @Bean
    @ConfigurationProperties(prefix = "numcolonnes.pacific")
    public InfoColonnesPacificProvider infoColonnesPacificProvider() {
        return new InfoColonnesPacificProvider();
    }

    @Bean
    @ConfigurationProperties(prefix = "numcolonnes.ratp")
    public InfoColonnesRATPProvider infoColonnesRATPProvider() {
        return new InfoColonnesRATPProvider();
    }
}