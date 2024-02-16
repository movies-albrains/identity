package io.albrains.moviebrains.moviebrainsidentity.config.keycloak

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "keycloak")
data class KeycloakProperties(
    val clientId: String,
    val clientSecret: String,
    val url: String,
    val realm: String,
    val clientName: String
)