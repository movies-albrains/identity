package io.albrains.moviebrains.identity

import io.albrains.moviebrains.identity.config.keycloak.KeycloakProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = [KeycloakProperties::class])
class MovieBrainsIdentityApplication

fun main(args: Array<String>) {
    runApplication<MovieBrainsIdentityApplication>(*args)
}
