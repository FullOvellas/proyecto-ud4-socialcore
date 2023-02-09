package com.aad.proyectoud4socialcore;

import com.google.maps.GeoApiContext;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import dev.hilla.Nonnull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotEnvException;
import org.springframework.context.annotation.Bean;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "com.aad.proyectoud4")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(destroyMethod = "shutdown")
    public GeoApiContext geoApiContext() {

        Dotenv dotenv = Dotenv.configure().load();
        GeoApiContext.Builder builder = new GeoApiContext.Builder();

        return builder
                .apiKey(dotenv.get("MAPS_API_KEY"))
                .disableRetries()
                .build();

    }

}
