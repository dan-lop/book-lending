package de.ktc.booklending.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Büchermanagement-Service",
                description = "Dieser Service ermöglicht das Anzeigen, Anlegen, Bearbeiten und löschen von Büchern."
        )
)
public class OpenAPIConfiguration {
}
