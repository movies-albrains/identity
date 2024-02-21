package io.albrains.moviebrains.identity.main.authenticate.service

import io.albrains.moviebrains.identity.config.keycloak.KeycloakProperties
import io.albrains.moviebrains.identity.main.user.service.domain.UserAuthRequest
import io.github.oshai.kotlinlogging.KotlinLogging
import org.keycloak.OAuth2Constants
import org.keycloak.admin.client.KeycloakBuilder
import org.keycloak.representations.AccessTokenResponse
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UserAuthenticationService(private val keycloakProperties: KeycloakProperties) {

    fun authenticate(userAuthRequest: UserAuthRequest): AccessTokenResponse {
        logger.info { "Authenticating user ${userAuthRequest.username}" }
        return KeycloakBuilder.builder()
            .realm(keycloakProperties.realm)
            .serverUrl(keycloakProperties.url)
            .clientId(keycloakProperties.clientId)
            .clientSecret(keycloakProperties.clientSecret)
            .username(userAuthRequest.username)
            .password(userAuthRequest.password)
            .grantType(OAuth2Constants.PASSWORD)
            .build()
            .tokenManager()
            .accessToken
    }
}