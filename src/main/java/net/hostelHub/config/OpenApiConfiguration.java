package net.hostelHub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "MosTech",
                        email = "hunsumoses7@gmail.com",
                        url = "https://www.linkedin.com/in/moses-hunsu-94b86a169/"
                ),
                description = "OpenApi documentation for Hostel Hub",
                 title = "HostelHub OpenApi Specification",
                version = "v1",
                license = @License(
                        name = "License name",
                        url = "https://url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://multi-tenant-hostel-application.onrender.com"
                ),
                @Server(
                        description = "Prod ENV",
                        url = "https://multi-tenancy-hostel-software-production.up.railway.app/"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {
}
