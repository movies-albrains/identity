package io.albrains.moviebrains.identity.common

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

private val logger = KotlinLogging.logger {}

@TestConfiguration(proxyBeanMethods = false)
class KeycloakTestContainersConfig {

    @Bean
    fun keycloakContainer(registry: DynamicPropertyRegistry): KeycloakContainer {
        val keycloak = KeycloakContainer()
            .withRealmImportFile("keycloak/realm.json")
        registry.add("spring.security.oauth2.resource-server.jwt.issuer-uri")
            { "${keycloak.authServerUrl}realms/SpringBootKeycloak" }
        registry.add("keycloak.url")
            { keycloak.authServerUrl }
        return keycloak
    }
}