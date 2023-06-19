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
    @ConfigurationProperties
    public GaresProvider garesConfigProvider() {
        return new GaresProvider();
    }
}
