package io.albrains.moviebrains.identity.main.user.infrastructure.adapter.keycloak

import io.albrains.moviebrains.identity.main.user.service.domain.UserRegistration
import io.albrains.moviebrains.identity.main.user.service.UserRegistrationProvider
import io.albrains.moviebrains.identity.main.user.service.domain.UserResponse
import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.CredentialRepresentation
import org.keycloak.representations.idm.UserRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class KeycloakUserRegistrationProvider(private val keycloak: Keycloak): UserRegistrationProvider {

    @Value("\${keycloak.realm}")
    private lateinit var keycloakRealm: String

    override fun createUser(userRegistration: UserRegistration): UserResponse {
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

    override fun deleteUserById(id: String) {
        keycloak.realm(keycloakRealm)
            .users()
            .delete(id)
    }
}