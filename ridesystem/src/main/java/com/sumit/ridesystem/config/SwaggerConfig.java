package com.sumit.ridesystem.config;

import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        final String securitySchemeName =
                "bearerAuth";

        return new OpenAPI()

                // =================================
                // SECURITY
                // =================================

                .addSecurityItem(

                        new SecurityRequirement()

                                .addList(
                                        securitySchemeName
                                )
                )

                .schemaRequirement(

                        securitySchemeName,

                        new SecurityScheme()

                                .name(
                                        securitySchemeName
                                )

                                .type(
                                        SecurityScheme.Type.HTTP
                                )

                                .scheme(
                                        "bearer"
                                )

                                .bearerFormat(
                                        "JWT"
                                )
                )

                // =================================
                // API INFO
                // =================================

                .info(

                        new Info()

                                .title(
                                        "CABZI Backend API"
                                )

                                .version(
                                        "1.0"
                                )

                                .description(
                                        "CABZI Ride Sharing, Driver Verification, Parcel Delivery and Admin Management Backend APIs"
                                )

                                .contact(

                                        new Contact()

                                                .name(
                                                        "Sumit Kumar"
                                                )

                                                .email(
                                                        "sumit257210@gmail.com"
                                                )
                                )
                );
    }
}