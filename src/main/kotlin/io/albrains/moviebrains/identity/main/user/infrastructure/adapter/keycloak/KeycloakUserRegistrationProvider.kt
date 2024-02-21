package io.albrains.moviebrains.identity.main.user.infrastructure.adapter.keycloak

import io.albrains.moviebrains.identity.main.user.service.domain.UserRegistration
import io.albrains.moviebrains.identity.main.user.service.UserRegistrationProvider
import io.albrains.moviebrains.identity.main.user.service.domain.UserResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

private val logger = KotlinLogging.logger {}

@Service
class KeycloakUserRegistrationProvider(private val keycloak: Keycloak): UserRegistrationProvider {

    @Value("\${keycloak.realm}")
    private lateinit var keycloakRealm: String

    override fun createUser(userRegistration: UserRegistration): UserResponse {
        logger.info { "Création de l'utilisateur ${userRegistration.firstName} ${userRegistration.lastName}" }

        val userRepresentation = UserRepresentation()
        userRepresentation.isEnabled = true
        userRepresentation.username = userRegistration.username
        userRepresentation.email = userRegistration.email
        // TODO vérification email
        userRepresentation.isEmailVerified = true
        userRepresentation.firstName = userRegistration.firstName
        userRepresentation.lastName = userRegistration.lastName
        // TODO rôle de l'utilisateur

        val credentialRepresentation = CredentialRepresentation()
        credentialRepresentation.value = userRegistration.password
        credentialRepresentation.isTemporary = false
        credentialRepresentation.type = CredentialRepresentation.PASSWORD

        userRepresentation.credentials = listOf(credentialRepresentation)

        val usersResource = keycloak
            .realm(keycloakRealm)
            .users()
        val response = usersResource.create(userRepresentation)

        if (Objects.equals(response.status, 201)) {
            return UserResponse(
                username = userRegistration.username,
                email = userRegistration.email,
                firstName = userRegistration.firstName,
                lastName = userRegistration.lastName
            )
        }
        TODO("to implement")
    }

    override fun getUserById(id: String): UserResponse {
        logger.debug { "Récupération des informations de l'utilisateur $id" }
        val userRepresentation = keycloak.realm(keycloakRealm)
            .users()
            .get(id)
            .toRepresentation()

        return UserResponse(
            username = userRepresentation.username,
            email = userRepresentation.email,
            firstName = userRepresentation.firstName,
            lastName = userRepresentation.lastName
        )
    }

    override fun resetPassword(id: String, newPassword: String) {
        logger.info { "Mise à jour du mot de passe de l'utilisateur $id" }
        val credentialRepresentation = CredentialRepresentation()
        credentialRepresentation.value = newPassword
        credentialRepresentation.isTemporary = false
        credentialRepresentation.type = CredentialRepresentation.PASSWORD

        keycloak.realm(keycloakRealm)
            .users()
            .get(id)
            .resetPassword(credentialRepresentation)
    }

    override fun deleteUserById(id: String) {
        logger.info { "Suppression de l'utilisateur $id" }
        keycloak.realm(keycloakRealm)
            .users()
            .delete(id)
    }
}
