package io.albrains.moviebrains.identity.common

import dasniko.testcontainers.keycloak.KeycloakContainer
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

private val logger = KotlinLogging.logger {}

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class KeycloakTestContainers {
    companion object {
        @Container
        private val keycloak
                : KeycloakContainer = KeycloakContainer()
            .withRealmImportFile("keycloak/realm.json")
    }

    init {
        keycloak.start()
        System.setProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri",
            "${keycloak.authServerUrl}realms/SpringBootKeycloak")
    }
}