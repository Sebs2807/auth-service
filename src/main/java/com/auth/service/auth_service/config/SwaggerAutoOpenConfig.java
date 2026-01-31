package com.auth.service.auth_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

@Component
@Profile("!prod")
public class SwaggerAutoOpenConfig {

    private static final String BROWSER_OPENED_FLAG = "swagger.browser.opened";

    @Value("${server.port:8080}")
    private String serverPort;

    @EventListener(ApplicationReadyEvent.class)
    public void openSwaggerUI() {
        String swaggerUrl = "http://localhost:" + serverPort + "/swagger-ui.html";

        System.out.println("\n========================================");
        System.out.println("Auth Service iniciado correctamente!");
        System.out.println("Swagger UI: " + swaggerUrl);
        System.out.println("========================================\n");

        // No abre otro navegador
        if (System.getProperty(BROWSER_OPENED_FLAG) != null) {
            System.out.println("Ya hay una instancia del navegador abierta");
            return;
        }

        // Navegador ya abierto (flag)
        System.setProperty(BROWSER_OPENED_FLAG, "true");

        // Intenta abrir el navegador automáticamente
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(URI.create(swaggerUrl));
            } catch (IOException e) {
                System.out.println("No se pudo abrir el navegador automáticamente.");
                System.out.println("Abre manualmente: " + swaggerUrl);
            }
        } else {
            try {
                Runtime.getRuntime().exec("cmd /c start " + swaggerUrl);
            } catch (IOException e) {
                System.out.println("No se pudo abrir el navegador automáticamente.");
                System.out.println("Abre manualmente: " + swaggerUrl);
            }
        }
    }
}
